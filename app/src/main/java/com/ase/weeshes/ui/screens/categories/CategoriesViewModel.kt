package com.ase.weeshes.ui.screens.categories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ase.weeshes.domain.model.Category
import com.ase.weeshes.domain.usecase.categories.CreateCategoryUseCase
import com.ase.weeshes.domain.usecase.categories.DeleteCategoryUseCase
import com.ase.weeshes.domain.usecase.categories.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val getCategories: GetCategoriesUseCase,
    private val createCategory: CreateCategoryUseCase,
    private val delCategory: DeleteCategoryUseCase,
) : ViewModel() {

    private var _uiState = MutableStateFlow(CategoriesUiState())
    val uiState: StateFlow<CategoriesUiState> = _uiState

    init {
        retrieveCategories()
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

    fun addCategory(name: String, icon: String) {
        viewModelScope.launch {
            createCategory(name, icon)
        }
    }

    fun deleteCategory(id: String) {
        viewModelScope.launch {
            delCategory(id)
        }
    }
}

data class CategoriesUiState(
    val categories: List<Category> = emptyList(),
)