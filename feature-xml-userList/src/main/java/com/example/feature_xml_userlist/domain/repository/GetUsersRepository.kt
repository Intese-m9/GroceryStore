package com.example.feature_xml_userlist.domain.repository

import com.example.feature_xml_userlist.domain.models.User
import com.example.feature_xml_userlist.domain.models.UserDomain

interface GetUsersRepository {
    suspend fun getUsers():List<UserDomain>
}