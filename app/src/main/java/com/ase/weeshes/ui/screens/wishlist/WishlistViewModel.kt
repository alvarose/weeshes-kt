package com.ase.weeshes.ui.screens.wishlist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.ase.weeshes.domain.model.Category
import com.ase.weeshes.domain.model.Wishlist
import com.ase.weeshes.domain.usecase.categories.GetCategoriesUseCase
import com.ase.weeshes.domain.usecase.products.CreateProductUseCase
import com.ase.weeshes.domain.usecase.wishlists.GetWishlistByIdUseCase
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
    private val getCategories: GetCategoriesUseCase,
    private val createProduct: CreateProductUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow(WishlistDetailUiState())
    val uiState: StateFlow<WishlistDetailUiState> = _uiState

    private val routeData = savedStateHandle.toRoute<NavigationScreens.WishlistDetail>()

    init {
        retrieveCategories()
        retrieveWishlist()
    }

    private fun retrieveCategories() {
        viewModelScope.launch {
            getCategories().collect { categories ->
                _uiState.update {
                    it.copy(categories = categories)
                }
            }
        }
    }

    private fun retrieveWishlist() {
        viewModelScope.launch {
            findWishlistById(routeData.wishlistId).collect { wishlist ->
                _uiState.update {
                    it.copy(wishlist = wishlist)
                }
            }
        }
    }

    fun addProduct(wishlistId: String, name: String, link: String, category: String) {
        viewModelScope.launch {
            createProduct(wishlistId, name, link, category)
        }
    }
}

data class WishlistDetailUiState(
    val wishlist: Wishlist? = null,
    val categories: List<Category> = emptyList(),
)