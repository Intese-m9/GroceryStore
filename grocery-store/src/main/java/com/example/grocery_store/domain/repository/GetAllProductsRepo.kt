package com.example.grocery_store.domain.repository

import com.example.grocery_store.domain.models.ProductsFeature

interface GetAllProductsRepo {
    suspend fun loadProducts():List<ProductsFeature>
}