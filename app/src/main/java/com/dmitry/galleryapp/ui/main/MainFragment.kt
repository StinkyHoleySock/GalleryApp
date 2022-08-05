package com.dmitry.galleryapp.ui.main

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmitry.galleryapp.R
import com.dmitry.galleryapp.databinding.FragmentMainBinding
import com.dmitry.galleryapp.model.Albums

private var _binding: FragmentMainBinding? = null
private val binding get() = _binding!!

private lateinit var adapter: AlbumAdapter

class MainFragment: Fragment(R.layout.fragment_main) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentMainBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //Получение пермишена на чтение из файловой системы
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {

                adapter = AlbumAdapter(getAllShownImagesPath(requireContext()))

                val layoutManager = LinearLayoutManager(context)
                binding.rvAlbums.layoutManager = layoutManager
                binding.rvAlbums.adapter = adapter

            } else {
                Toast.makeText(context, R.string.permission_denied, Toast.LENGTH_LONG).show()
            }
        }

        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)



    }

    //Зануляем байндинг во избежание утечек памяти
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


private fun loadData(context: Context) {
    getAllShownImagesPath(context)
}

//Эта функция должна возвращать список альбомов, пока что это что-то страшное
@SuppressLint("Recycle")
private fun getAllShownImagesPath(context: Context): ArrayList<Albums> {

    val cursor: Cursor
    var cursorBucket: Cursor
    val listOfAllImages = ArrayList<String>()
    var absolutePathOfImage: String?
    val albumsList = ArrayList<Albums>()


    val BUCKET_GROUP_BY = "1) GROUP BY 1,(2"
    val BUCKET_ORDER_BY = "MAX(datetaken) DESC"

    val uri: Uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI

    val projection = arrayOf(
        MediaStore.Images.ImageColumns.BUCKET_ID,
        MediaStore.Images.ImageColumns.BUCKET_DISPLAY_NAME,
        MediaStore.Images.ImageColumns.DATE_TAKEN,
        MediaStore.Images.ImageColumns.DATA)

    cursor = context.contentResolver.query(uri, projection, BUCKET_GROUP_BY, null, BUCKET_ORDER_BY)!!

    val columnIndexData: Int = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA)
    val columnIndexFolderName: Int = cursor
        .getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
    while (cursor.moveToNext()) {
        absolutePathOfImage = cursor.getString(columnIndexData)
        Log.d("title_apps", "bucket name:" + cursor.getString(columnIndexData))

        val selectionArgs = arrayOf("%" + cursor.getString(columnIndexFolderName) + "%")
        val selection = MediaStore.Images.Media.DATA + " like ? "
        val projectionOnlyBucket = arrayOf(MediaStore.MediaColumns.DATA, MediaStore.Images.Media.BUCKET_DISPLAY_NAME)

        cursorBucket = context.contentResolver.query(uri, projectionOnlyBucket, selection, selectionArgs, null)!!
        Log.d("title_apps", "bucket size:" + cursorBucket.count)

        if (absolutePathOfImage != "" && absolutePathOfImage != null) {
            listOfAllImages.add(absolutePathOfImage)
            albumsList.add(Albums(cursor.getString(columnIndexFolderName), absolutePathOfImage, false))
        }
    }
    return albumsList
}

