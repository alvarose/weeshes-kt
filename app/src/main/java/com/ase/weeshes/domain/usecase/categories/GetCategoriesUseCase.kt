package com.ase.weeshes.domain.usecase.categories

import com.ase.weeshes.domain.model.Category
import com.ase.weeshes.domain.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val repository: CategoriesRepository) {
    suspend operator fun invoke(): Flow<List<Category>> = repository.getCategories()
}