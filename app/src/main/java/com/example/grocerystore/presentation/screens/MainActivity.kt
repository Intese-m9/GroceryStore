package com.example.grocerystore.presentation.screens

import android.os.Bundle
import android.widget.Toast

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import com.example.grocerystore.ui.theme.GroceryStoreTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.grocerystore.presentation.utils.events.UIStateEvent
import com.example.grocerystore.presentation.viewmodels.ProductViewModelFeature

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val viewModelFeature: ProductViewModelFeature by viewModels()
        setContent {
            GroceryStoreTheme {
                val navController = rememberNavController()
                LaunchedEffect(Unit) {
                    viewModelFeature.showToast.collect { stateEvent ->
                        when (stateEvent) {
                            is UIStateEvent.ShowToast -> {
                                Toast.makeText(
                                    this@MainActivity,
                                    stateEvent.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                NavHost(
                    navController = navController, startDestination = "allProducts"
                ) {
                    composable("allProducts") {
                        ProductScreen(
                            viewModelFeature = viewModelFeature,
                            navController = navController
                        )
                    }
                    composable("addToCartProducts") {
                        CartProductScreen(
                            viewModelFeature = viewModelFeature
                        )
                    }
                }
            }
        }
    }
}
