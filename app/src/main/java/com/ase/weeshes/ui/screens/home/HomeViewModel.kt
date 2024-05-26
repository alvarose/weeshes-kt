package com.ase.weeshes.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ase.weeshes.domain.model.Wishlist
import com.ase.weeshes.domain.usecase.auth.LogoutUseCase
import com.ase.weeshes.domain.usecase.wishlists.CreateWishlistUseCase
import com.ase.weeshes.domain.usecase.wishlists.GetWishlistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val getWishlists: GetWishlistsUseCase,
    private val createWishlist: CreateWishlistUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        retrieveWishlists()
    }

    private fun retrieveWishlists() {
        viewModelScope.launch(Dispatchers.IO) {
            getWishlists().collect { wishlists ->
                _uiState.update {
                    it.copy(wishlists = wishlists)
                }
            }
        }
    }

    fun addWishlist(name: String, icon: String) {
        viewModelScope.launch(Dispatchers.IO) {
            createWishlist(name, icon)
        }
    }

    fun logout(onLoggedOut: () -> Unit) {
        viewModelScope.launch { logoutUseCase.invoke() }
        onLoggedOut()
    }
}

data class HomeUiState(
    val wishlists: List<Wishlist> = emptyList(),
)

