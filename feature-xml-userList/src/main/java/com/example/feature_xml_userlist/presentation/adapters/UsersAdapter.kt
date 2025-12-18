package com.example.feature_xml_userlist.presentation.adapters

import android.graphics.Color
import android.util.Log
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
            Log.d("DEBUG", "Adapter: payloads = $payloads, size = ${payloads.size}")
            Log.d(
                "DEBUG",
                "Adapter: payloads types = ${payloads.map { it?.javaClass?.simpleName }}"
            )

            for (payload in payloads) {
                when (payload) {
                    is List<*> -> {
                        if (payload.contains("read_change")) {
                            Log.d("DEBUG", "Found read_change!")
                            holder.updateReadColor(user.isRead)
                        }
                        if (payload.contains("name_change")) {
                            holder.nameChange(user.name)
                        }
                        if (payload.contains("email_change")) {
                            holder.emailChange(user.email)
                        }
                    }

                    else -> {
                        Log.d("DEBUG", "Payload is not List: ${payload?.javaClass}")
                        onBindViewHolder(holder, position)
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
            binding.userName.animate()
                .scaleX(1.2f).scaleY(1.2f)
                .setDuration(200)
                .withEndAction {
                    binding.userName.animate()
                        .scaleX(1f).scaleY(1f)
                        .setDuration(200)
                        .start()
                }
                .start()
        }
    }
}
