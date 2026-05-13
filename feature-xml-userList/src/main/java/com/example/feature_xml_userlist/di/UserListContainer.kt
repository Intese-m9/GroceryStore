package com.example.feature_xml_userlist.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.core_network.NetworkFactory
import com.example.feature_xml_userlist.data.remote.dto.RemoteDataSourceImpl
import com.example.feature_xml_userlist.data.remote.dto.api.UserApi
import com.example.feature_xml_userlist.data.repository.GetUsersRepositoryImpl
import com.example.feature_xml_userlist.domain.usecase.GetUsersUseCase
import com.example.feature_xml_userlist.domain.utils.DataStoreManager
import com.example.feature_xml_userlist.presentation.viewmodels.SharedViewModel
import com.example.feature_xml_userlist.presentation.viewmodels.UserViewModel

class UserListContainer(
    appContext: Context
) {
    private val dataStoreManager = DataStoreManager(appContext)
    private val getAllUsersUseCase = GetUsersUseCase(
        usersRepository = GetUsersRepositoryImpl(
            remoteDataSource = RemoteDataSourceImpl(
                api = NetworkFactory.createRetrofit(baseUrl = "https://fake-json-api.mock.beeceptor.com")
                    .create(
                        UserApi::class.java
                    )
            )
        )
    )

    fun provideSharedViewModelFactory(
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return SharedViewModel(dataStoreManager) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

    fun provideUserViewModelFactory(
    ): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return UserViewModel(getAllUsersUseCase) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }

}
