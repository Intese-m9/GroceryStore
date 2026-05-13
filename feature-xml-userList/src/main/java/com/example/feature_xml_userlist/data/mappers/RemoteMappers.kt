package com.example.feature_xml_userlist.data.mappers

import com.example.feature_xml_userlist.data.models.UserDTO
import com.example.feature_xml_userlist.domain.models.UserDomain
import com.example.feature_xml_userlist.presentation.models.UserPresentation

fun UserDTO.toDomain(): UserDomain{
    return UserDomain(
        id = this.id,
        name = this.userName,
        email = this.userEmail
    )
}

fun UserDomain.toPresentation(): UserPresentation {
    return UserPresentation(
        id = this.id,
        name = this.name,
        email = this.email
    )
}