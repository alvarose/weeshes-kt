package com.ase.weeshes.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ase.weeshes.data.network.controllers.AuthController
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authController: AuthController,
) : ViewModel() {

    private var _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private var _loginError = MutableStateFlow<String?>(null)
    val loginError = _loginError.asStateFlow()

    fun login(
        email: String,
        pass: String,
        onLoggedIn: () -> Unit,
    ) {
        if (checkUserData(email, pass))
            viewModelScope.launch {
                _isLoading.value = true
                val result = withContext(Dispatchers.IO) {
                    authController.login(email, pass)
                }
                if (result != null) {
                    onLoggedIn()
                } else {
                    _loginError.value = "Ha ocurrido un error"
                }
                _isLoading.value = false
            }
    }

    fun signUp(
        name: String,
        email: String,
        pass: String,
        onRegistered: () -> Unit,
    ) {
        if (checkUserData(email, pass, name))
            viewModelScope.launch {
                _isLoading.value = true
                val result = withContext(Dispatchers.IO) {
                    authController.signUp(name, email, pass)
                }
                if (result != null) {
                    onRegistered()
                } else {
                    _loginError.value = "Ha ocurrido un error"
                }
                _isLoading.value = false
            }
    }

    private fun checkUserData(email: String, pass: String, name: String? = null): Boolean {

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val isEmailValid = email.matches(Regex(emailPattern))

        val isPassValid = pass.length >= 6

        val isNameValid = (name == null || name.isNotEmpty())

        _loginError.value = when {
            !isEmailValid -> "Introduce un email válido"
            !isPassValid -> "La contraseña debe tener al menos 6 carácteres"
            !isNameValid -> "Introduce un nombre válido"
            else -> null
        }

        return isEmailValid && isPassValid && isNameValid
    }

    fun clearError() {
        _loginError.value = null
    }
}

