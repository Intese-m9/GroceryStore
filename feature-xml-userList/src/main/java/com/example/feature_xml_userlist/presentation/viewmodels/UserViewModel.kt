package com.example.feature_xml_userlist.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_xml_userlist.domain.models.User
import com.example.feature_xml_userlist.presentation.actions.UserEvent
import com.example.feature_xml_userlist.presentation.utils.UserUIState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<UserUIState> = MutableStateFlow(UserUIState())
    val uiState get() = _uiState.asStateFlow()

    private val _events: MutableSharedFlow<UserEvent> = MutableSharedFlow()
    val events get() = _events.asSharedFlow()

    //error точно нужно указывать явно на null, если в первичном конструкторе уже null
    fun loadUsers() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, error = null) }
            try {
                delay(1000)
                val users = listOf<User>(
                    User(1, "John Doe", "john@example.com"),
                    User(2, "Jane Smith", "jane@example.com"),
                    User(3, "Bob Johnson", "bob@example.com")
                )
                _uiState.update { it.copy(isLoading = false, users = users) }
                _events.emit(UserEvent.ShowMessage(message = "Users loaded successfully"))
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(isLoading = false, error = "Failed to load users: ${e.message}")
                }
            }
        }
    }

    fun selectedUser(user: User) {
        _uiState.update { it.copy(selectedUser = user) }
        viewModelScope.launch {
            _events.emit(UserEvent.ShowMessage(message = "Selected: ${user.name}"))
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}