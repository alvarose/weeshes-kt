package com.ase.weeshes.domain.repository

import com.ase.weeshes.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {

    suspend fun createCategory(name: String, icon: String): Boolean

    suspend fun deleteCategory(id: String): Boolean

    suspend fun getCategories(): Flow<List<Category>>

}