package com.example.feature_xml_userlist.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_xml_userlist.domain.models.User
import com.example.feature_xml_userlist.domain.usecase.GetUsers
import com.example.feature_xml_userlist.presentation.actions.UserEvent
import com.example.feature_xml_userlist.presentation.models.UserPresentation
import com.example.feature_xml_userlist.presentation.utils.UserUIState
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class UserViewModel(
    private val getUsers: GetUsers
) : ViewModel() {
    private val _uiState: MutableStateFlow<UserUIState> = MutableStateFlow(UserUIState())
    val uiState get() = _uiState.asStateFlow()

    private val _events: MutableSharedFlow<UserEvent> = MutableSharedFlow()
    val events get() = _events.asSharedFlow()

    init {
        loadUsers()
    }

    fun loadUsers() {
       viewModelScope.launch {
           val getUsers = getUsers.getUsers()
           _uiState.update { currentState ->
               currentState.copy(
                   isLoading = false,
                   users = getUsers
               )
           }
       }
    }

    fun movePositionInRecyclerView(fromPosition: Int, toPosition: Int) {
        _uiState.update { currentState ->
            val currentUser = currentState.users[fromPosition]

            val before = currentState.users.take(fromPosition)
            val after = currentState.users.drop(fromPosition + 1)

            val listWithoutCurrentUser = before + after

            val newPositionUserItem =
                listWithoutCurrentUser
                    .take(toPosition) + currentUser + listWithoutCurrentUser.drop(
                    toPosition
                )

            val totalList = newPositionUserItem.mapIndexed { index, user ->
                user.copy(position = index)
            }

            currentState.copy(users = totalList)

        }
    }

    fun selectedUser(user: UserPresentation) {
        _uiState.update { currentUser ->
            currentUser.copy(
                users = currentUser.users.map {
                    if (it.id == user.id) {
                        it.copy(isRead = true)
                    } else it
                }
            )
        }
        viewModelScope.launch {
            _events.emit(UserEvent.ShowMessage(message = "Selected: ${user.name}"))
        }
    }

    fun clearError() {
        _uiState.update { it.copy(error = null) }
    }
}