package com.example.feature_xml_userlist.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_xml_userlist.domain.utils.DataStoreManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SharedViewModel(
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    init {
        viewModelScope.launch {
            dataStoreManager.counterFlow.collect { savedValue ->
                _counter.value = savedValue
            }
        }
    }

    private val _counter: MutableStateFlow<Int> = MutableStateFlow(0)
    val counter: StateFlow<Int> get() = _counter

    fun increment() {
        _counter.update { count ->
            val result = count.inc()
            viewModelScope.launch {
                dataStoreManager.saveCounter(result)
            }
            result
        }
    }

    fun decrement() {
        _counter.update { count ->
            val result = count.dec()
            viewModelScope.launch {
                dataStoreManager.saveCounter(result)
            }
            result
        }
    }

}