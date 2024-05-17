package com.ase.weeshes.ui.screens.wishlist.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ase.weeshes.domain.model.Wishlist
import com.ase.weeshes.domain.usecase.GetWishlistByIdUseCase
import com.ase.weeshes.ui.navigation.NavigationScreens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val findWishlistById: GetWishlistByIdUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow(WishlistDetailUiState())
    val uiState: StateFlow<WishlistDetailUiState> = _uiState

    private val routeData = savedStateHandle.toRoute<NavigationScreens.WishlistDetail>()

    init {
        viewModelScope.launch {
            findWishlistById(routeData.wishlistId).collect { wishlist ->
                _uiState.update {
                    it.copy(wishlist = wishlist)
                }
            }
        }
    }

}

data class WishlistDetailUiState(
    val wishlist: Wishlist? = null,
)