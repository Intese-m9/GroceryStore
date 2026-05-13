package com.example.feature_xml_userlist.domain.usecase

import com.example.feature_xml_userlist.presentation.models.UserPresentation

interface GetUsers {
    suspend fun getUsers():List<UserPresentation>
}