package com.dmitry.galleryapp.ui.image

import android.Manifest
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.dmitry.galleryapp.R
import com.dmitry.galleryapp.databinding.FragmentImageBinding
import com.dmitry.galleryapp.ui.rename.RenameFragment

private var _binding: FragmentImageBinding? = null
private val binding get() = _binding!!

class ImageFragment: Fragment(R.layout.fragment_image) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentImageBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageName = arguments?.getString(NAME_KEY)
        val imageUri = arguments?.getParcelable<Uri?>(IMAGE_KEY)

        with(binding) {

            Glide.with(ivImage)
                .asBitmap()
                .load(imageUri)
                .into(ivImage)

            tvImageName.text = imageName
        }

        binding.btnRename.setOnClickListener {

            findNavController().navigate(
                R.id.action_imageFragment_to_renameFragment,
                bundleOf(
                    RenameFragment.NAME_KEY to imageName,
                    RenameFragment.URI_KEY to imageUri
                )
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val IMAGE_KEY = "IMAGE"
        const val NAME_KEY = "NAME"
    }
}