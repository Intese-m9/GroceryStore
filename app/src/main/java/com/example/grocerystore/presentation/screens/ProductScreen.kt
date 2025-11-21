package com.example.grocerystore.presentation.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.grocerystore.presentation.viewmodels.ProductViewModelFeature
import com.example.grocerystore.ui.components.ProductItem

@Composable
fun ProductScreen(
    viewModelFeature: ProductViewModelFeature
) {
    val products by viewModelFeature.filteredQuery.collectAsState()
    val searchQuery by viewModelFeature.searchQuery.collectAsState()
    val categories = viewModelFeature.categories
    val selectedCategory by viewModelFeature.selectedCategory.collectAsState()
    Scaffold(topBar = {
        Column {
            TextField(
                modifier = Modifier
                    .padding(start = 15.dp, end = 15.dp)
                    .fillMaxWidth(),
                value = searchQuery,
                onValueChange = viewModelFeature::onSearchQueryChanged
            )
            LazyRow(
                modifier = Modifier.padding(vertical = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(categories) { category ->
                    FilterChip(
                        selected = category == selectedCategory,
                        onClick = { viewModelFeature.onCategorySelected(category) },
                        label = { Text(text = category) },
                    )
                }
            }
        }
    }) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            items(products) { product ->
                ProductItem(
                    product = product, addToProductCart = viewModelFeature::addProductToCart
                )
            }
        }
    }
}