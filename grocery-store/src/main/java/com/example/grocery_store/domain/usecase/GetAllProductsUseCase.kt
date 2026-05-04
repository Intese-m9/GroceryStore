package com.example.grocery_store.domain.usecase

import com.example.grocery_store.domain.models.ProductsFeature
import com.example.grocery_store.domain.repository.GetAllProductsRepo

class GetAllProductsUseCase(
    private val getAllProductsRepo: GetAllProductsRepo
) {
    suspend fun getProducts(): List<ProductsFeature> {
        return getAllProductsRepo.loadProducts()
    }
}