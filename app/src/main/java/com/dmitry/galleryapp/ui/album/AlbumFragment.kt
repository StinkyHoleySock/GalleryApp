package com.dmitry.galleryapp.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dmitry.galleryapp.R
import com.dmitry.galleryapp.databinding.FragmentAlbumBinding
import com.dmitry.galleryapp.model.ImageDataSource
import com.dmitry.galleryapp.repository.ImageRepository
import kotlinx.coroutines.Dispatchers


private var _binding: FragmentAlbumBinding? = null
private val binding get() = _binding!!

class AlbumFragment: Fragment(R.layout.fragment_album) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //Инициализация байндинга
        _binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Получение id альбома на который кликнул пользователь
        val albumId = arguments?.getString(ALBUM_KEY)

        val galleryRepository = ImageRepository(
            ImageDataSource(requireActivity().application.contentResolver), Dispatchers.IO
        )

        val viewModel = AlbumViewModel(albumId, requireActivity().application, galleryRepository)
        val layoutManager = GridLayoutManager(context, 3)

        with(binding) {

            rvImages.layoutManager = layoutManager
            viewModel.images.observe(viewLifecycleOwner) {
                rvImages.adapter = ImageAdapter(it)
            }


            progressCircular.visibility = View.GONE
            rvImages.visibility = View.VISIBLE
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
