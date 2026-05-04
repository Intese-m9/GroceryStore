package com.example.grocery_store.presentation.utils.events

sealed class UIStateEvent {
    data class ShowToast(val message: String) : UIStateEvent()
}
