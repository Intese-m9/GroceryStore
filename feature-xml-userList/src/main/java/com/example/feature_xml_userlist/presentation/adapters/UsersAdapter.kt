package com.example.feature_xml_userlist.presentation.adapters

import android.graphics.Color
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
        parent: ViewGroup, viewType: Int
    ): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: UserViewHolder, position: Int
    ) {
        holder.bindFull(getItem(position))
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int, payloads: List<Any?>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        } else {
            val user = getItem(position)


            for (payload in payloads) {
                when (payload) {
                    "name_change" -> {
                        holder.nameChange(user.name)
                    }

                    "email_change" -> {
                        holder.emailChange(user.email)
                    }

                    "read_change" -> {
                        holder.updateReadColor(user.isRead)
                    }
                }
            }
        }

    }

    inner class UserViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bindFull(user: User) {
            binding.userName.text = user.name
            binding.userEmail.text = user.email
            updateReadColor(user.isRead)
            binding.root.setOnClickListener {
                onUserClick(user)
            }
        }

        fun nameChange(name: String) {
            binding.userName.text = name
        }

        fun emailChange(email: String) {
            binding.userEmail.text = email
        }

        fun updateReadColor(isRead: Boolean) {
            val colorState = if (isRead) Color.GREEN else Color.RED
            binding.userName.setTextColor(colorState)
        }
    }
}
