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

}