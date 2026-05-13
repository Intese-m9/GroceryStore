package com.example.feature_xml_userlist.data.repository

import com.example.feature_xml_userlist.data.mappers.toDomain
import com.example.feature_xml_userlist.data.remote.dto.RemoteDataSource
import com.example.feature_xml_userlist.domain.models.UserDomain
import com.example.feature_xml_userlist.domain.repository.GetUsersRepository

class GetUsersRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : GetUsersRepository {
    override suspend fun getUsers(): List<UserDomain> = remoteDataSource.getUsers().map { userDTO ->
        userDTO.toDomain()
    }
}