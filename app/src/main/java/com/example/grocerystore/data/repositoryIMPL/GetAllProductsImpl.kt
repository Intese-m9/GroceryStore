package com.example.grocerystore.data.repositoryIMPL

import com.example.grocerystore.domain.models.ProductsFeature
import com.example.grocerystore.domain.repository.GetAllProductsRepo
import kotlinx.coroutines.delay

class GetAllProductsImpl : GetAllProductsRepo {
    override suspend fun loadProducts(): List<ProductsFeature> {
        delay(3000)
        return listOf(
            ProductsFeature("1", "Apple", 2.99, "Fruits", "https://example.com/apple.jpg"),
            ProductsFeature("2", "Banana", 1.99, "Fruits", "https://example.com/banana.jpg"),
            ProductsFeature("3", "Milk", 3.49, "Dairy", "https://example.com/milk.jpg"),
            ProductsFeature("4", "Bread", 2.29, "Bakery", "https://example.com/bread.jpg"),
            ProductsFeature("5", "Eggs", 4.99, "Dairy", "https://example.com/eggs.jpg"),
            ProductsFeature("6", "Tomato", 1.49, "Vegetables", "https://example.com/tomato.jpg"),
            ProductsFeature("7", "Cheese", 5.99, "Dairy", "https://example.com/cheese.jpg"),
            ProductsFeature("8", "Orange", 3.99, "Fruits", "https://example.com/orange.jpg")
        )
    }
}