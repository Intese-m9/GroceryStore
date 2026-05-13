package com.example.feature_xml_userlist.presentation.utils

import com.example.feature_xml_userlist.domain.models.User
import com.example.feature_xml_userlist.presentation.models.UserPresentation

data class UserUIState(
    val isLoading:Boolean = false,
    val users:List<UserPresentation> = emptyList(),
    val selectedUser:User? = null,
    val error:String? = null,
)