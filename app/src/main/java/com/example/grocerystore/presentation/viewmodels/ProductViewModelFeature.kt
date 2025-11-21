package com.example.grocerystore.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.grocerystore.domain.models.ProductsFeature
import com.example.grocerystore.domain.usecase.GetAllProductsUseCase
import com.example.grocerystore.presentation.utils.events.UIStateEvent
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
import timber.log.Timber
import kotlin.text.contains

class ProductViewModelFeature(
    private val getAllProductsUseCase: GetAllProductsUseCase
) : ViewModel() {
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
        viewModelScope.launch {
            _products.update { currentState ->
                val result = getAllProductsUseCase.getProducts()
                currentState.plus(result)
            }
            showMessage("Данные загружены")
            _allProducts = _products.value
        }
    }

    fun addProductToCart(product: ProductsFeature) {
        _productCart.update { currentItem ->
            showMessage("Товар добавлен в корзину")
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
