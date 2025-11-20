package com.example.grocerystore.presentation.utils.events

sealed class UIStateEvent {
    data class ShowToast(val message: String) : UIStateEvent()
}
