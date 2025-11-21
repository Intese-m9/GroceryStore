package com.example.grocerystore.domain.repository

import com.example.grocerystore.domain.models.ProductsFeature

interface GetAllProductsRepo {
    suspend fun loadProducts():List<ProductsFeature>
}