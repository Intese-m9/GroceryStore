package com.example.grocerystore.data.repositoryIMPL

import com.example.grocerystore.data.FakeUserApiService
import com.example.grocerystore.data.mappers.ProductsMapper
import com.example.grocerystore.domain.models.ProductsFeature
import com.example.grocerystore.domain.repository.GetAllProductsRepo
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