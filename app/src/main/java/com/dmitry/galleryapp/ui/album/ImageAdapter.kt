package com.dmitry.galleryapp.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dmitry.galleryapp.R
import com.dmitry.galleryapp.databinding.ImageItemBinding
import com.dmitry.galleryapp.model.Image
import com.dmitry.galleryapp.ui.image.ImageFragment

class ImageAdapter(
    private val listArray: List<Image>,
) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ImageItemBinding.inflate(inflater, parent, false)
        return ImageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val image = listArray[position]
        with(holder.binding) {

            Glide.with(ivPreview)
                .asBitmap()
                .load(image.uri)
                .into(ivPreview)

            tvImageName.text = image.name

            //Навигация и передача аргументов
            this.imageItem.setOnClickListener {
                imageItem.findNavController().navigate(
                    R.id.action_albumFragment_to_imageFragment,
                    bundleOf(
                        ImageFragment.IMAGE_KEY to image.uri,
                        ImageFragment.NAME_KEY to image.name
                    )
                )
            }
        }
    }

    override fun getItemCount() = listArray.size


    class ImageViewHolder(
        val binding: ImageItemBinding
    ) :RecyclerView.ViewHolder(binding.root)
}