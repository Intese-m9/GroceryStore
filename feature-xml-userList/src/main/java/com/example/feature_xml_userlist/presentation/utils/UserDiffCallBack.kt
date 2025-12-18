package com.example.feature_xml_userlist.presentation.utils

import androidx.recyclerview.widget.DiffUtil
import com.example.feature_xml_userlist.domain.models.User

object UserDiffCallBack : DiffUtil.ItemCallback<User>() {
    override fun areItemsTheSame(
        oldItem: User,
        newItem: User
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: User,
        newItem: User
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: User, newItem: User): Any? {
        val payload = mutableListOf<String>()

        if (oldItem.name != newItem.name) {
            payload.add("name_change")
        }

        if (oldItem.email != newItem.email) {
            payload.add("email_change")
        }

        if (oldItem.isRead != newItem.isRead){
            payload.add("read_change")
        }

        return null
    }

}