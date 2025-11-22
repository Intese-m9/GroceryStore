package com.example.grocerystore.presentation.screens

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
import androidx.compose.ui.unit.dp
import com.example.grocerystore.ui.theme.GroceryStoreTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.grocerystore.data.FakeUserApiService
import com.example.grocerystore.data.mappers.ProductsMapper
import com.example.grocerystore.data.repositoryIMPL.GetAllProductsImpl
import com.example.grocerystore.domain.usecase.GetAllProductsUseCase
import com.example.grocerystore.presentation.utils.events.UIStateEvent
import com.example.grocerystore.presentation.viewmodels.MyViewModelFactory
import com.example.grocerystore.presentation.viewmodels.ProductViewModelFeature

class MainActivity : ComponentActivity() {
    private val viewModelFeature: ProductViewModelFeature by viewModels {
        MyViewModelFactory(
            GetAllProductsUseCase(
                GetAllProductsImpl(
                    fakeUserApiService = FakeUserApiService(),
                    productsMapper = ProductsMapper()
                )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GroceryStoreTheme {
                val navController = rememberNavController()
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

