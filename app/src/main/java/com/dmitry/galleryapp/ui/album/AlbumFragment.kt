package com.dmitry.galleryapp.ui.album

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dmitry.galleryapp.R
import com.dmitry.galleryapp.databinding.FragmentAlbumBinding

private var _binding: FragmentAlbumBinding? = null
private val binding get() = _binding!!

class AlbumFragment: Fragment(R.layout.fragment_album) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentAlbumBinding.inflate(inflater, container, false)

        return binding.root
    }


    //Зануляем байндинг во избежание утечек памяти
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}