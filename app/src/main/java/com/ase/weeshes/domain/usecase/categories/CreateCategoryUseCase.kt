package com.ase.weeshes.domain.usecase.categories

import com.ase.weeshes.domain.repository.CategoriesRepository
import javax.inject.Inject

class CreateCategoryUseCase @Inject constructor(private val repository: CategoriesRepository) {
    suspend operator fun invoke(name: String, icon: String) = repository.createCategory(name, icon)
}