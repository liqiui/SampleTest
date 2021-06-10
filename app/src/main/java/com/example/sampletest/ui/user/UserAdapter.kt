package com.example.sampletest.ui.user

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.sampletest.data.User
import com.example.sampletest.databinding.UserItemBinding

class UserAdapter(private val onClickListener: OnClickListener): ListAdapter<User, UserAdapter.UserItemHolder>(DiffCallback) {
    class OnClickListener(val clickListener: (user: User) -> Unit) {
        fun onClick( user: User) = clickListener( user)
    }

    class UserItemHolder(private var binding: UserItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.user = user
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserItemHolder {
        return UserItemHolder( UserItemBinding.inflate( LayoutInflater.from( parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: UserItemHolder, position: Int) {
        val user = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(user)
        }
        holder.bind(user)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<User>() {
        override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem.id === newItem.id
        }

        override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
            return oldItem == newItem
        }

    }
}