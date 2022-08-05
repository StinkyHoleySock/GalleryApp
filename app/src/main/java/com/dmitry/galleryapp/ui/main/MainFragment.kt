package com.dmitry.galleryapp.ui.main

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.dmitry.galleryapp.R
import com.dmitry.galleryapp.databinding.FragmentMainBinding

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


        //Получение пермишена на чтение из файловой системы
        val permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { isGranted ->
            if (isGranted) {
                loadData()
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


private fun loadData() {

}

