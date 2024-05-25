package com.ase.weeshes.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor() : ViewModel() {

    private val _splashShowFlow = MutableStateFlow(true)
    val isSplashShow = _splashShowFlow.asStateFlow()

    init {
        viewModelScope.launch {
            delay(3000)
            _splashShowFlow.value = false
        }
    }
}