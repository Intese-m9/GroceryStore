package com.example.grocerystore.data.di

import com.example.grocerystore.data.FakeUserApiService
import com.example.grocerystore.data.mappers.ProductsMapper
import com.example.grocerystore.data.repositoryIMPL.GetAllProductsImpl
import com.example.grocerystore.domain.usecase.GetAllProductsUseCase
import com.example.grocerystore.presentation.viewmodels.MyViewModelFactory

class AppContainer {

    private val productsMapper = ProductsMapper()
    private val fakeUserApiService = FakeUserApiService()
    private val getAllProductsImpl = GetAllProductsImpl(
        fakeUserApiService = fakeUserApiService, productsMapper = productsMapper
    )

    val viewModelFeatureFactory = MyViewModelFactory(
        GetAllProductsUseCase(
            getAllProductsImpl
        )
    )
}