package com.example.feature_xml_userlist.data.remote.dto

import com.example.feature_xml_userlist.data.models.UserDTO
import com.example.feature_xml_userlist.data.remote.dto.api.UserApi

class RemoteDataSourceImpl(
    private val api: UserApi
) : RemoteDataSource {
    override suspend fun getUsers(): List<UserDTO> = api.getUsers()
}