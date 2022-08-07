package com.dmitry.galleryapp.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dmitry.galleryapp.R
import com.dmitry.galleryapp.databinding.AlbumItemBinding
import com.dmitry.galleryapp.model.Album
import com.dmitry.galleryapp.ui.album.AlbumFragment

class AlbumAdapter(
    private val listArray: List<Album>,
) : RecyclerView.Adapter<AlbumAdapter.AlbumViewHolder>() {



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

            this.albumItem.setOnClickListener() {
                albumItem.findNavController().navigate(
                    R.id.action_mainFragment_to_albumFragment,
                    bundleOf(AlbumFragment.ALBUM_KEY to album.id)
                )
            }
        }
    }

    override fun getItemCount() = listArray.size

    class AlbumViewHolder(
        val binding: AlbumItemBinding
    ) : RecyclerView.ViewHolder(binding.root)


}