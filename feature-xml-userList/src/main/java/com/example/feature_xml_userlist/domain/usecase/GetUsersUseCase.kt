package com.example.feature_xml_userlist.domain.usecase

import com.example.feature_xml_userlist.data.mappers.toPresentation
import com.example.feature_xml_userlist.domain.repository.GetUsersRepository
import com.example.feature_xml_userlist.presentation.models.UserPresentation

class GetUsersUseCase(
    private val usersRepository: GetUsersRepository
) : GetUsers {
    override suspend fun getUsers(): List<UserPresentation> {
        return usersRepository.getUsers().map { userDomain -> userDomain.toPresentation() }
    }
}