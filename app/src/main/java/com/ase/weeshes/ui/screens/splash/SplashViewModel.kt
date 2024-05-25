package com.ase.weeshes.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ase.weeshes.data.network.controllers.AuthController
import com.ase.weeshes.ui.navigation.NavigationScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authController: AuthController,
) : ViewModel() {

    private val _splashShowFlow = MutableStateFlow(true)
    val isSplashShow = _splashShowFlow.asStateFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            _splashShowFlow.value = false
        }
    }

    fun checkDestination(): NavigationScreens {
        val isLogged = isUserLogged()

        return if (isLogged) NavigationScreens.Home
        else NavigationScreens.Auth
    }

    private fun isUserLogged() = authController.isLoggedIn()
}