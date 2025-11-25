package com.example.feature_xml_userlist.presentation.utils

import com.example.feature_xml_userlist.domain.models.User

data class UserUIState(
    val isLoading:Boolean = false,
    val users:List<User> = emptyList(),
    val selectedUser:User? = null,
    val error:String? = null
)