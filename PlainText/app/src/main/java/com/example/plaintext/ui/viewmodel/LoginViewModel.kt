package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.plaintext.data.repository.PasswordDBStore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.launch
import javax.inject.Inject

data class LoginViewState(
    val username: String = "",
    val password: String = "",
    val isLoading: Boolean = false
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val store: PasswordDBStore
) : ViewModel() {

    var uiState by mutableStateOf(LoginViewState())
        private set

    fun onUsernameChange(username: String) {
        uiState = uiState.copy(username = username)
    }

    fun onPasswordChange(password: String) {
        uiState = uiState.copy(password = password)
    }

    fun login(
        expectedUser: String,
        expectedPass: String,
        onSuccess: () -> Unit,
        onError: () -> Unit
    ) {
        viewModelScope.launch {
            uiState = uiState.copy(isLoading = true)
            // Simula um delay de rede/processamento
            kotlinx.coroutines.delay(500)
            uiState = uiState.copy(isLoading = false)

            if (uiState.username == expectedUser && uiState.password == expectedPass) {
                onSuccess()
            } else {
                onError()
            }
        }
    }
}
