package com.example.feature_xml_userlist.presentation.models

data class UserPresentation(
    val id:Long,
    val name:String,
    val email:String,
    val isRead: Boolean = false,
    val position:Int? = null
)