package com.example.grocery_store.presentation.screens

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.grocery_store.ui.theme.GroceryStoreTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.core.navigation.navigateToUserList
import com.example.grocery_store.data.di.GroceryContainer
import com.example.grocery_store.presentation.utils.events.UIStateEvent
import com.example.grocery_store.presentation.viewmodels.ProductViewModelFeature

class MainActivity : ComponentActivity() {
    private val featureContainer by lazy {
        GroceryContainer()
    }
    private val viewModelFeature: ProductViewModelFeature by viewModels {
        featureContainer.viewModelFeatureFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GroceryStoreTheme {
                val navController = rememberNavController()
                val context = LocalContext.current
                LaunchedEffect(viewModelFeature) {
                    viewModelFeature.showToast.collect { stateEvent ->
                        when (stateEvent) {
                            is UIStateEvent.ShowToast -> {
                                Toast.makeText(
                                    this@MainActivity, stateEvent.message, Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }

                Scaffold(topBar = {
                    Text(
                        text = "Grocery Store", modifier = Modifier.windowInsetsPadding(
                            WindowInsets.statusBars
                        )
                    )
                }, bottomBar = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            modifier = Modifier.clickable {
                                navController.navigate("allProducts")
                            },
                            text = "All"
                        )
                        Text(
                            modifier = Modifier.clickable {
                                navController.navigate("addToCartProducts")
                            }, text = "Cart"
                        )
                        Text(
                            modifier = Modifier.clickable {
                                context.navigateToUserList()
                            }, text = "Go to Feature"
                        )
                    }
                }) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "allProducts",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("allProducts") {
                            ProductScreen(
                                viewModelFeature = viewModelFeature
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
}