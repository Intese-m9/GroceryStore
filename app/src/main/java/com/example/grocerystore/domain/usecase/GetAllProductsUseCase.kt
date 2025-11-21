package com.example.grocerystore.domain.usecase

import com.example.grocerystore.domain.models.ProductsFeature
import com.example.grocerystore.domain.repository.GetAllProductsRepo

class GetAllProductsUseCase(
    private val getAllProductsRepo: GetAllProductsRepo
) {
    suspend fun getProducts(): List<ProductsFeature> {
        return getAllProductsRepo.loadProducts()
    }
}