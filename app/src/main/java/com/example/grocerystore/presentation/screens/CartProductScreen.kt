package com.example.grocerystore.presentation.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.grocerystore.presentation.viewmodels.ProductViewModelFeature
import com.example.grocerystore.ui.components.CartProductItem
import androidx.compose.foundation.lazy.items

@Composable
fun CartProductScreen(
    viewModelFeature: ProductViewModelFeature
) {
    val cartProduct by viewModelFeature.productCart.collectAsState()
    val allSum by viewModelFeature.showAllItemsInCart.collectAsState()
    val countProduct by viewModelFeature.showAllItemsCountInCart.collectAsState()
    Column {
        IconButton(onClick = {
            viewModelFeature.clearCart()
        }) {
            Icon(Icons.Default.Delete, contentDescription = "Delete")
        }
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(cartProduct) { product ->
                CartProductItem(
                    product = product, removeCartProduct = viewModelFeature::removeProductInCart
                )
            }
        }
        Text(
            text = if (allSum == 0.0) "Empty" else "All items in cart: $allSum"
        )
        Text(
            text = "Count in Cart: $countProduct"
        )
        Spacer(modifier = Modifier.size(50.dp))
    }
}

