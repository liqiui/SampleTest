package com.example.sampletest

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sampletest.data.User
import com.example.sampletest.ui.user.UserAdapter

@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,
                     users: List<User>?) {
    val adapter = recyclerView.adapter as UserAdapter
    adapter.submitList(users)
}