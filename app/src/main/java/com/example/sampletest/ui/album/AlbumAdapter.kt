package com.example.sampletest.ui.album

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sampletest.data.Photo
import com.example.sampletest.data.User
import com.example.sampletest.databinding.AlbumItemBinding
import com.example.sampletest.databinding.UserItemBinding
import com.example.sampletest.ui.user.UserAdapter

class AlbumAdapter(private val onClickListener: AlbumAdapter.OnClickListener): ListAdapter<Photo, AlbumAdapter.AlbumItemHolder>(DiffCallback) {
    class OnClickListener(val clickListener: (photo: Photo) -> Unit) {
        fun onClick( photo: Photo) = clickListener( photo)
    }

    class AlbumItemHolder(private var binding: AlbumItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(photo: Photo) {
            binding.photo = photo
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumItemHolder {
        return AlbumItemHolder( AlbumItemBinding.inflate( LayoutInflater.from( parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AlbumItemHolder, position: Int) {
        val photo = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(photo)
        }
        holder.bind(photo)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Photo>() {
        override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean {
            return oldItem == newItem
        }

    }
}