package com.example.feature_xml_userlist.presentation.utils

import android.util.Log
import androidx.recyclerview.widget.DiffUtil
import com.example.feature_xml_userlist.domain.models.User
import com.example.feature_xml_userlist.presentation.models.UserPresentation

object UserDiffCallBack : DiffUtil.ItemCallback<UserPresentation>() {
    override fun areItemsTheSame(
        oldItem: UserPresentation,
        newItem: UserPresentation
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: UserPresentation,
        newItem: UserPresentation
    ): Boolean {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: UserPresentation, newItem: UserPresentation): Any? {
        val payload = mutableListOf<String>()

        if (oldItem.name != newItem.name) {
            payload.add("name_change")
        }

        if (oldItem.email != newItem.email) {
            payload.add("email_change")
        }

        if (oldItem.isRead != newItem.isRead) {
            payload.add("read_change")
            Log.d("DEBUG", "Diff: isRead changed, payload = $payload")
        }

        return payload.ifEmpty { null }
    }

}