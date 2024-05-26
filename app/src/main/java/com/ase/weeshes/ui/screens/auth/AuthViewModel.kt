package com.ase.weeshes.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ase.weeshes.data.network.response.Response
import com.ase.weeshes.domain.usecase.auth.LoginUseCase
import com.ase.weeshes.domain.usecase.auth.RegisterUseCase
import com.google.firebase.auth.AuthResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private var _authFlow = MutableSharedFlow<Response<AuthResult>>()
    val authFlow = _authFlow

    private var _authError = MutableStateFlow<String?>(null)
    val authError = _authError.asStateFlow()

    fun login(email: String, pass: String) = viewModelScope.launch(Dispatchers.IO) {
        if (checkUserData(email, pass))
            loginUseCase.invoke(email, pass).collect { response ->
                _authFlow.emit(response)
            }
    }

    fun register(name: String, email: String, pass: String) = viewModelScope.launch(Dispatchers.IO) {
        registerUseCase.invoke(name, email, pass).collect { response ->
            _authFlow.emit(response)
        }
    }

    private fun checkUserData(email: String, pass: String, name: String? = null): Boolean {

        val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"
        val isEmailValid = email.matches(Regex(emailPattern))
        val isPassValid = pass.length >= 6
        val isNameValid = (name == null || name.isNotEmpty())

        _authError.value = when {
            !isEmailValid -> "Introduce un email válido"
            !isPassValid -> "La contraseña debe tener al menos 6 carácteres"
            !isNameValid -> "Introduce un nombre válido"
            else -> null
        }

        return isEmailValid && isPassValid && isNameValid
    }

    fun clearError() {
        _authError.value = null
    }
}

