package com.ase.weeshes.domain.usecase.categories

import com.ase.weeshes.domain.repository.CategoriesRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(private val repository: CategoriesRepository) {
    suspend operator fun invoke(id: String) = repository.deleteCategory(id)
}