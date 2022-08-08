package com.dmitry.galleryapp.ui.main

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.dmitry.galleryapp.GalleryDataSource
import com.dmitry.galleryapp.R
import com.dmitry.galleryapp.databinding.FragmentMainBinding
import com.dmitry.galleryapp.repository.GalleryRepository
import kotlinx.coroutines.Dispatchers

private var _binding: FragmentMainBinding? = null
private val binding get() = _binding!!

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

        val galleryRepository = GalleryRepository(
            GalleryDataSource(requireActivity().application.contentResolver), Dispatchers.IO
        )
        val viewModel = MainViewModel(requireActivity().application, galleryRepository)
        val layoutManager = LinearLayoutManager(context)

        //Получение пермишена на чтение из файловой системы
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {

                viewModel.albums.observe(viewLifecycleOwner) {
                    binding.rvAlbums.adapter = AlbumAdapter(it)
                }

                binding.rvAlbums.layoutManager = layoutManager

            } else {
                Toast.makeText(context, R.string.permission_denied, Toast.LENGTH_LONG).show()
            }
        }

        permissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


