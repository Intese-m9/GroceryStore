package com.example.feature_xml_userlist.presentation.actions

sealed class UserEvent {
    data class ShowMessage(val message: String) : UserEvent()
    object NavigateToDetails  : UserEvent()
}