package com.example.sampletest

import android.widget.ImageView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sampletest.data.Photo
import com.example.sampletest.data.User
import com.example.sampletest.ui.album.AlbumAdapter
import com.example.sampletest.ui.user.UserAdapter
import com.squareup.picasso.Picasso

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     users: List<User>?) {
    val adapter = recyclerView.adapter as UserAdapter
    adapter.submitList(users)
}

@BindingAdapter("albumData")
fun bindAlbumData(recyclerView: RecyclerView,
                     photos: List<Photo>?) {
    val adapter = recyclerView.adapter as AlbumAdapter
    adapter.submitList(photos)
}

@BindingAdapter("thumbnailUrl")
fun bindImageThumbnailView(imageView: ImageView, thumbnailUrl: String?) {
    thumbnailUrl?.let {
        val imageUri = thumbnailUrl.toUri().buildUpon().scheme("https").build()
        Picasso.get().load(imageUri)
                .fit()
                .into(imageView)
    }
}

