package com.example.grocerystore.presentation.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.navigation.NavHostController
import com.example.grocerystore.presentation.viewmodels.ProductViewModelFeature
import com.example.grocerystore.ui.components.ProductItem

@Composable
fun ProductScreen(
    viewModelFeature: ProductViewModelFeature,
    navController: NavHostController
) {
    val products by viewModelFeature.filteredQuery.collectAsState()
    val searchQuery by viewModelFeature.searchQuery.collectAsState()
    val categories = viewModelFeature.categories
    val selectedCategory by viewModelFeature.selectedCategory.collectAsState()
    Scaffold(topBar = {
        Column {
            Text(
                text = "Grocery Store", modifier = Modifier.padding(16.dp)
            )
            TextField(
                modifier = Modifier.padding(16.dp),
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


    }, bottomBar = {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "All")
            Text(
                modifier = Modifier.clickable {
                    navController.navigate("addToCartProducts")
                }, text = "Cart"
            )
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