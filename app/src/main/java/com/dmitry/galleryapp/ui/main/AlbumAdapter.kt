package com.dmitry.galleryapp.ui.main

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.dmitry.galleryapp.R
import com.dmitry.galleryapp.databinding.AlbumItemBinding
import com.dmitry.galleryapp.model.Albums

class AlbumAdapter(
    private val listArray: ArrayList<Albums>,
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AlbumItemBinding.inflate(inflater, parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = listArray[position]
        with(holder.binding) {
            ivLastImage.setImageResource(R.drawable.ic_launcher_background)
            tvAlbumName.text = album.folderName
            tvNumberPhotos.text = album.numberOfImages
        }
    }

    override fun getItemCount() = listArray.size

    class AlbumViewHolder(
        val binding: AlbumItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}