package com.ase.weeshes.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ase.weeshes.domain.usecase.auth.CanAccessUseCase
import com.ase.weeshes.domain.usecase.auth.IsLoggedInUseCase
import com.ase.weeshes.ui.navigation.NavigationScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val isLoggedInUseCase: IsLoggedInUseCase,
    private val canAccessUseCase: CanAccessUseCase,
) : ViewModel() {

    private val _splashShowFlow = MutableStateFlow(true)
    val isSplashShow = _splashShowFlow.asStateFlow()

    private val _startDestination = MutableStateFlow<NavigationScreens?>(null)
    val startDestination = _startDestination.asStateFlow()

    private val _isLoggedInState = MutableStateFlow(false)
    private val _canAccess = MutableStateFlow(false)

    init {
        checkAccess()
    }

    private fun checkDestination() {
        _startDestination.value = when (true) {
            !_canAccess.value -> NavigationScreens.Version
            _isLoggedInState.value -> NavigationScreens.Home
            else -> NavigationScreens.Auth
        }
        _splashShowFlow.value = false
    }

    private fun isLoggedIn() = viewModelScope.launch {
        isLoggedInUseCase.invoke().collect {
            _isLoggedInState.value = it
        }
    }

    private fun checkAccess() = viewModelScope.launch {
        canAccessUseCase.invoke().collect { access ->
            _canAccess.value = access
            if (access) isLoggedIn()
            checkDestination()
        }
    }
}