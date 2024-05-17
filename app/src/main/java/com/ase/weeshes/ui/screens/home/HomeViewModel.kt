package com.ase.weeshes.ui.screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ase.weeshes.domain.model.Wishlist
import com.ase.weeshes.domain.usecase.GetWishlistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getWishlists: GetWishlistsUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        retrieveWishlists()
    }

    private fun retrieveWishlists() {
        viewModelScope.launch {
            getWishlists().collect { wishlists ->
                _uiState.update {
                    it.copy(wishlists = wishlists)
                }
            }
        }
    }
}

data class HomeUiState(
    val wishlists: List<Wishlist> = emptyList(),
)

