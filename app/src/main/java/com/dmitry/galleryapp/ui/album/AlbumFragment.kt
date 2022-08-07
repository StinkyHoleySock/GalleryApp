package com.dmitry.galleryapp.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmitry.galleryapp.Gallery
import com.dmitry.galleryapp.R
import com.dmitry.galleryapp.databinding.FragmentAlbumBinding

private var _binding: FragmentAlbumBinding? = null
private val binding get() = _binding!!

private lateinit var adapter: ImageAdapter

class AlbumFragment: Fragment(R.layout.fragment_album) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAlbumBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val gallery = Gallery(requireContext())
        val albumId = arguments?.getString(ALBUM_KEY)
        val images: List<Gallery.Image> = gallery.findImagesInAlbum(albumId.toString())
        val layoutManager = GridLayoutManager(context, 3)
        adapter = ImageAdapter(images)



        with(binding) {
            rvPhotos.layoutManager = layoutManager
            rvPhotos.adapter = adapter
        }
    }


    //Зануляем байндинг во избежание утечек памяти
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val ALBUM_KEY = "KEY"
    }
}