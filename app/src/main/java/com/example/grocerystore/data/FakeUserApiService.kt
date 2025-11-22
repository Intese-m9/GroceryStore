package com.example.grocerystore.data

import com.example.grocerystore.data.model.ProductsFeatureDTO
import kotlinx.coroutines.delay

class FakeUserApiService {

    suspend fun getAllProducts(): List<ProductsFeatureDTO> {
        delay(1000)
        return listOf(
            ProductsFeatureDTO("1", "Apple", 2.99, "Fruits", "https://example.com/apple.jpg"),
            ProductsFeatureDTO("2", "Banana", 1.99, "Fruits", "https://example.com/banana.jpg"),
            ProductsFeatureDTO("3", "Milk", 3.49, "Dairy", "https://example.com/milk.jpg"),
            ProductsFeatureDTO("4", "Bread", 2.29, "Bakery", "https://example.com/bread.jpg"),
            ProductsFeatureDTO("5", "Eggs", 4.99, "Dairy", "https://example.com/eggs.jpg"),
            ProductsFeatureDTO("6", "Tomato", 1.49, "Vegetables", "https://example.com/tomato.jpg"),
            ProductsFeatureDTO("7", "Cheese", 5.99, "Dairy", "https://example.com/cheese.jpg"),
            ProductsFeatureDTO("8", "Orange", 3.99, "Fruits", "https://example.com/orange.jpg")
        )
    }

}