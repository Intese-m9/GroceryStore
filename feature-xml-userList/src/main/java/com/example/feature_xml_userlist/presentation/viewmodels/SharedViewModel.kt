package com.example.feature_xml_userlist.presentation.viewmodels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class SharedViewModel : ViewModel() {

    private val _counter: MutableStateFlow<Int> = MutableStateFlow(0)
    val counter: StateFlow<Int> get() = _counter

    fun increment() {
        _counter.update { count ->
            count.inc()
        }
    }

    fun decrement() {
        _counter.update { count ->
            count.dec()
        }
    }

}