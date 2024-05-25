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

private const val LOGIN_SCREEN = "login"
private const val SIGN_UP_SCREEN = "signUp"

@Composable
fun AuthScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onLoggedIn: () -> Unit,
) {
    var screen by remember { mutableStateOf(LOGIN_SCREEN) }

    val error by viewModel.loginError.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    LaunchedEffect(error) {
        error?.let {
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
                LOGIN_SCREEN -> LoginScreen(viewModel = viewModel, onLoggedIn = onLoggedIn) { screen = SIGN_UP_SCREEN }
                SIGN_UP_SCREEN -> SignUpScreen(viewModel = viewModel, onLoggedIn = onLoggedIn) { screen = LOGIN_SCREEN }
            }
        }
    }
}

@Composable
fun LoginScreen(
    viewModel: AuthViewModel,
    onLoggedIn: () -> Unit,
    onScreen: () -> Unit,
) {
    var email by remember { mutableStateOf("alvaroserranoe@gmail.com") }
    var pass by remember { mutableStateOf("alvaro") }


    TextField(value = email, onValueChange = { email = it }, placeholder = { Text("Email") })
    TextField(value = pass, onValueChange = { pass = it }, placeholder = { Text("Contraseña") })
    Button(onClick = { viewModel.login(email, pass) { onLoggedIn() } }) {
        Text("Iniciar sesión")
    }
    TextButton(onClick = onScreen) {
        Text("Crear una cuenta")
    }
}

@Composable
fun SignUpScreen(
    viewModel: AuthViewModel,
    onLoggedIn: () -> Unit,
    onScreen: () -> Unit,
) {
    var name by remember { mutableStateOf("Álvaro S") }
    var email by remember { mutableStateOf("alvaroserranoe@gmail.com") }
    var pass by remember { mutableStateOf("alvaro") }

    TextField(value = name, onValueChange = { name = it }, placeholder = { Text("Nombre") })
    TextField(value = email, onValueChange = { email = it }, placeholder = { Text("Email") })
    TextField(value = pass, onValueChange = { pass = it }, placeholder = { Text("Contraseña") })
    Button(onClick = { viewModel.signUp(name, email, pass) { onLoggedIn() } }) {
        Text("Continuar")
    }
    TextButton(onClick = onScreen) {
        Text("¿Ya tienes una cuenta? Inicia sesión")
    }
}