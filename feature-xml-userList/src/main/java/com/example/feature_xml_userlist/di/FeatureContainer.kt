package com.example.feature_xml_userlist.di

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.feature_xml_userlist.domain.utils.DataStoreManager
import com.example.feature_xml_userlist.presentation.viewmodels.SharedViewModel

class FeatureContainer(
    appContext: Context
) {
    private val dataStoreManager = DataStoreManager(appContext)

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

}
