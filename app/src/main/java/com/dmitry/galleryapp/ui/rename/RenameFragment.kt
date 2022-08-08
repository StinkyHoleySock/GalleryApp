package com.dmitry.galleryapp.ui.rename

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.dmitry.galleryapp.R
import com.dmitry.galleryapp.databinding.FragmentRenameBinding

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

        //Получение аргументов изображения
        val imageName = arguments?.getString(NAME_KEY)
        val uri = arguments?.getParcelable<Uri>(URI_KEY)

        binding.etName.hint = imageName

        binding.tvApply.setOnClickListener {
            // val newName = binding.etName.text.toString()
            // TODO: function to rename image

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        const val NAME_KEY = "NAME"
        const val URI_KEY = "URI"
    }
}