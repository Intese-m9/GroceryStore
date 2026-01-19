package com.example.grocery_store.data.repositoryIMPL

import com.example.grocery_store.data.FakeUserApiService
import com.example.grocery_store.data.mappers.ProductsMapper
import com.example.grocery_store.domain.models.ProductsFeature
import com.example.grocery_store.domain.repository.GetAllProductsRepo
import kotlinx.coroutines.delay

class GetAllProductsImpl(
    private val fakeUserApiService: FakeUserApiService,
    private val productsMapper: ProductsMapper
) : GetAllProductsRepo {
    override suspend fun loadProducts(): List<ProductsFeature> {
        delay(3000)
        val result = fakeUserApiService.getAllProducts()
        return result.map { item ->
            productsMapper.mapToDomain(item)
        }
    }
}