package com.ase.weeshes.ui.screens.auth

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ase.weeshes.core.ex.toast
import com.ase.weeshes.data.network.response.Response
import com.ase.weeshes.ui.components.LoadingProgress
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.MutableSharedFlow

private const val LOGIN_SCREEN = "login"
private const val SIGN_UP_SCREEN = "signUp"

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoggedIn: () -> Unit,
) {
    var screen by remember { mutableStateOf(LOGIN_SCREEN) }
    val validationError by viewModel.authError.collectAsState()

    LaunchedEffect(validationError) {
        validationError?.let {
            it.toast()
            viewModel.clearError()
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            when (screen) {
                LOGIN_SCREEN -> LoginScreen(viewModel = viewModel) { screen = SIGN_UP_SCREEN }
                SIGN_UP_SCREEN -> SignUpScreen(viewModel = viewModel) { screen = LOGIN_SCREEN }
            }
        }
        AuthState(
            flow = viewModel.authFlow,
            onSuccess = { onLoggedIn() },
            onError = { it.toast() }
        )
    }
}

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onScreen: () -> Unit,
) {
    var email by remember { mutableStateOf("alvaroserranoe@gmail.com") }
    var pass by remember { mutableStateOf("alvaro") }

    TextField(value = email, onValueChange = { email = it }, placeholder = { Text("Email") })
    TextField(value = pass, onValueChange = { pass = it }, placeholder = { Text("Contraseña") })
    Button(onClick = { viewModel.login(email, pass) }) {
        Text("Iniciar sesión")
    }
    Button(onClick = { }) {
        Text("Iniciar sesión con Google")
    }
    TextButton(onClick = onScreen) {
        Text("Crear una cuenta")
    }
}

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel,
    onScreen: () -> Unit,
) {
    var name by remember { mutableStateOf("Álvaro S") }
    var email by remember { mutableStateOf("alvaroserranoe@gmail.com") }
    var pass by remember { mutableStateOf("alvaro") }

    TextField(value = name, onValueChange = { name = it }, placeholder = { Text("Nombre") })
    TextField(value = email, onValueChange = { email = it }, placeholder = { Text("Email") })
    TextField(value = pass, onValueChange = { pass = it }, placeholder = { Text("Contraseña") })
    Button(onClick = { viewModel.register(name, email, pass) }) {
        Text("Continuar")
    }
    TextButton(onClick = onScreen) {
        Text("¿Ya tienes una cuenta? Inicia sesión")
    }
}

@Composable
fun AuthState(
    flow: MutableSharedFlow<Response<AuthResult>>,
    onSuccess: () -> Unit,
    onError: (String) -> Unit,
) {
    val isLoading = remember { mutableStateOf(false) }
    if (isLoading.value) LoadingProgress()
    LaunchedEffect(Unit) {
        flow.collect {
            when (it) {
                is Response.Error -> {
                    isLoading.value = false
                    onError(it.message)
                }

                is Response.Success -> {
                    isLoading.value = false
                    onSuccess()
                }

                is Response.Loading -> isLoading.value = true
            }
        }
    }
}