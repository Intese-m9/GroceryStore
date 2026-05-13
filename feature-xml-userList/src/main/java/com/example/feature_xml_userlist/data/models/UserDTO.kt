package com.example.feature_xml_userlist.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserDTO(
    val id:Long,
    @SerialName("name")
    val userName:String,
    @SerialName("email")
    val userEmail:String
)