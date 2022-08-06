package com.dmitry.galleryapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dmitry.galleryapp.Gallery
import com.dmitry.galleryapp.databinding.AlbumItemBinding

class AlbumAdapter(
    private val listArray: List<Gallery.Album>,
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>()  {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = AlbumItemBinding.inflate(inflater, parent, false)
        return AlbumViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumViewHolder, position: Int) {
        val album = listArray[position]
        with(holder.binding) {

            Glide.with(ivLastImage)
                .asBitmap()
                .load(album.uri)
                .into(ivLastImage)

            tvAlbumName.text = album.name
            tvNumberPhotos.text = album.count.toString()
        }
    }

    override fun getItemCount() = listArray.size

    class AlbumViewHolder(
        val binding: AlbumItemBinding
    ) : RecyclerView.ViewHolder(binding.root)
}