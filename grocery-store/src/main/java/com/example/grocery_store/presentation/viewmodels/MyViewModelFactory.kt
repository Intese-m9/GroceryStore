package com.example.grocery_store.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.grocery_store.domain.usecase.GetAllProductsUseCase

class MyViewModelFactory(
    private val useCase: GetAllProductsUseCase,
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProductViewModelFeature(useCase) as T
    }
}