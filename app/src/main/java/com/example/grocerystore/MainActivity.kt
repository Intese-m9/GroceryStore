package com.example.grocerystore

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grocerystore.ui.theme.GroceryStoreTheme
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.collections.plus
import kotlin.text.contains
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import timber.log.Timber

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GroceryStoreTheme {
                All()
            }
        }
    }
}

data class ProductsFeature(
    val id: String, val name: String, val price: Double, val category: String, val imageUrl: String
)

sealed class UIStateEvent {
    data class ShowToast(val message: String) : UIStateEvent()
}

class ProductViewModelFeature : ViewModel() {
    private val _products = MutableStateFlow<List<ProductsFeature>>(emptyList())

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow("All")
    val selectedCategory = _selectedCategory.asStateFlow()

    private val _categories = listOf("All", "Fruits", "Vegetables", "Dairy", "Bakery")
    val categories: List<String> get() = _categories

    private val _productCart = MutableStateFlow<List<ProductsFeature>>(emptyList())
    val productCart get() = _productCart

    private var _allProducts = listOf<ProductsFeature>()

    val showAllItemsInCart: StateFlow<Double> = _productCart.map { value ->
        value.sumOf { it.price }
    }.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = 0.0
    )

    val showAllItemsCountInCart: StateFlow<Int> = _productCart.map { value ->
        value.count()
    }.stateIn(
        scope = viewModelScope, started = SharingStarted.WhileSubscribed(5000), initialValue = 0
    )

    private val _showToast: MutableSharedFlow<UIStateEvent> = MutableSharedFlow(replay = 1)
    val showToast get() = _showToast

    val filteredQuery = combine(
        _products, _searchQuery
    ) { product, query ->
        if (query.isBlank()) {
            product
        } else {
            product.filter {
                it.name.contains(query.capitalize()) || it.category.contains(query.capitalize())
            }
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    init {
        loadProducts()
    }

    fun showMessage(message: String) {
        viewModelScope.launch {
            _showToast.emit(UIStateEvent.ShowToast(message))
        }
    }

    private fun loadProducts() {
        _products.value = listOf(
            ProductsFeature("1", "Apple", 2.99, "Fruits", "https://example.com/apple.jpg"),
            ProductsFeature("2", "Banana", 1.99, "Fruits", "https://example.com/banana.jpg"),
            ProductsFeature("3", "Milk", 3.49, "Dairy", "https://example.com/milk.jpg"),
            ProductsFeature("4", "Bread", 2.29, "Bakery", "https://example.com/bread.jpg"),
            ProductsFeature("5", "Eggs", 4.99, "Dairy", "https://example.com/eggs.jpg"),
            ProductsFeature("6", "Tomato", 1.49, "Vegetables", "https://example.com/tomato.jpg"),
            ProductsFeature("7", "Cheese", 5.99, "Dairy", "https://example.com/cheese.jpg"),
            ProductsFeature("8", "Orange", 3.99, "Fruits", "https://example.com/orange.jpg")
        )
        showMessage("Данные загружены")
        _allProducts = _products.value
    }

    fun addProductToCart(product: ProductsFeature) {
        _productCart.update { currentItem ->
            val newTask = product
            currentItem.plus(newTask)
        }
        Timber.tag("CARTLIST").i(productCart.value.toString())
    }

    fun removeProductInCart(product: ProductsFeature) {
        _productCart.update { currentITem ->
            showMessage("Товар удален из корзины")
            currentITem.minus(product)
        }
    }

    fun onSearchQueryChanged(query: String) {
        _searchQuery.value = query
        _products.value.map {
            it.name == query
        }
    }

    fun onCategorySelected(category: String) {
        _selectedCategory.value = category
        val allProducts = _allProducts
        _products.update {
            when (category) {
                "All" -> allProducts
                else -> allProducts.filter { it.category == category }
            }
        }
    }

    fun clearCart() {
        _productCart.update { currentState ->
            showMessage("Все товары удалены")
            currentState.minus(currentState)
        }
    }
}

@Composable
fun All() {
    @OptIn(ExperimentalMaterial3Api::class)
    val viewModel: ProductViewModelFeature = viewModel()
    val products by viewModel.filteredQuery.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()
    val categories = viewModel.categories
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val navController = rememberNavController()
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.showToast.collect { eventToast ->
            when (eventToast) {
                is UIStateEvent.ShowToast -> {
                    Toast.makeText(context, eventToast.message, Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    NavHost(
        navController = navController, startDestination = "allProducts"
    ) {
        composable("allProducts") {
            Scaffold(topBar = {
                Column {
                    Text(
                        text = "Grocery Store", modifier = Modifier.padding(16.dp)
                    )
                    TextField(
                        modifier = Modifier.padding(16.dp),
                        value = searchQuery,
                        onValueChange = viewModel::onSearchQueryChanged
                    )
                    LazyRow(
                        modifier = Modifier.padding(vertical = 8.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(categories) { category ->
                            FilterChip(
                                selected = category == selectedCategory,
                                onClick = { viewModel.onCategorySelected(category) },
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
                            product = product, addToProductCart = viewModel::addProductToCart
                        )
                    }
                }
            }
        }
        composable("addToCartProducts") {
            CartProductScreen(
                viewModelFeature = viewModel
            )
        }
    }
}

@Composable
fun ProductItem(
    product: ProductsFeature, addToProductCart: (ProductsFeature) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            AsyncImage(
                model = product.imageUrl,
                contentDescription = product.name,
                modifier = Modifier.size(64.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name, style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            IconButton(onClick = {
                addToProductCart.invoke(product)
                Timber.tag("CLICK").i("Add product to cart")
            }) {
                Icon(Icons.Default.Favorite, contentDescription = "Delete")
            }
        }
    }
}

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

@Composable
fun CartProductItem(
    product: ProductsFeature, removeCartProduct: (ProductsFeature) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = product.name, style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = "$${product.price}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = product.category,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.Gray
                )
            }
            IconButton(onClick = {
                removeCartProduct.invoke(product)
                Timber.tag("CLICK").i("Remove product to cart")
            }) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}
