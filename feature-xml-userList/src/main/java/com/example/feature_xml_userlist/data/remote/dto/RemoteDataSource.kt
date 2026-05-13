package com.example.feature_xml_userlist.data.remote.dto

import com.example.feature_xml_userlist.data.models.UserDTO

interface RemoteDataSource {
    suspend fun getUsers():List<UserDTO>
}