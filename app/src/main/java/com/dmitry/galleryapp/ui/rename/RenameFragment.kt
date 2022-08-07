package com.dmitry.galleryapp.ui.rename

import android.content.ClipData
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dmitry.galleryapp.Gallery
import com.dmitry.galleryapp.R
import com.dmitry.galleryapp.databinding.FragmentRenameBinding
import com.dmitry.galleryapp.model.Image

private var _binding: FragmentRenameBinding? = null
private val binding get() = _binding!!

class RenameFragment: DialogFragment(R.layout.fragment_rename) {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRenameBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val imageName = arguments?.getString(NAME_KEY)
        binding.etName.hint = imageName

        val image = Image(id = "", name = imageName)

        binding.tvApply.setOnClickListener {
            renameImage()
        }

    }

    private fun renameImage() {

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NAME_KEY = "NAME"
    }
}