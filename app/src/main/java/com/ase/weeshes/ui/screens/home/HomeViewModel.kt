package com.ase.weeshes.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ase.weeshes.core.ex.toast
import com.ase.weeshes.domain.model.Wishlist
import com.ase.weeshes.domain.usecase.app.CanAccessToApp
import com.ase.weeshes.domain.usecase.wishlists.CreateWishlistUseCase
import com.ase.weeshes.domain.usecase.wishlists.GetWishlistsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val canAccessToApp: CanAccessToApp,
    private val getWishlists: GetWishlistsUseCase,
    private val createWishlist: CreateWishlistUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            val canAccess = withContext(Dispatchers.IO) { canAccessToApp() }
            "Actualizar app? ${!canAccess}".toast()
        }
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

    fun addWishlist(name: String, icon: String) {
        viewModelScope.launch {
            createWishlist(name, icon)
        }
    }
}

data class HomeUiState(
    val wishlists: List<Wishlist> = emptyList(),
)

