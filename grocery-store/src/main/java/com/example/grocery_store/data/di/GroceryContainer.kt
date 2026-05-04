package com.example.grocery_store.data.di

import com.example.grocery_store.data.FakeUserApiService
import com.example.grocery_store.data.mappers.ProductsMapper
import com.example.grocery_store.data.repositoryIMPL.GetAllProductsImpl
import com.example.grocery_store.domain.usecase.GetAllProductsUseCase
import com.example.grocery_store.presentation.viewmodels.MyViewModelFactory

class GroceryContainer {
    private val productsMapper =
        ProductsMapper()
    private val fakeUserApiService =
        FakeUserApiService()
    private val getAllProductsImpl =
        GetAllProductsImpl(
            fakeUserApiService = fakeUserApiService, productsMapper = productsMapper
        )
    val viewModelFeatureFactory =
        MyViewModelFactory(
            GetAllProductsUseCase(
                getAllProductsImpl
            )
        )
}