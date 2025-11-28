package com.example.feature_xml_userlist.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.feature_xml_userlist.databinding.ItemUserBinding
import com.example.feature_xml_userlist.domain.models.User
import com.example.feature_xml_userlist.presentation.utils.UserDiffCallBack

class UsersAdapter(
    private val onUserClick: (User) -> Unit
) : ListAdapter<User, UsersAdapter.UserViewHolder>(UserDiffCallBack) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UserViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context))
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: UserViewHolder,
        position: Int
    ) {
        holder.bind(getItem(position))
    }

    inner class UserViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.userName.text = user.name
            binding.userEmail.text = user.email

            binding.root.setOnClickListener {
                onUserClick(user)
            }
        }
    }
}