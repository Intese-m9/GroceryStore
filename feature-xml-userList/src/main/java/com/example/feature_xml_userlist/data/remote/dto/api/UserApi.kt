package com.example.feature_xml_userlist.data.remote.dto.api

import com.example.feature_xml_userlist.data.models.UserDTO
import retrofit2.http.GET

interface UserApi {
    @GET("users")
    suspend fun getUsers():List<UserDTO>
}