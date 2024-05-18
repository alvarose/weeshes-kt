package com.ase.weeshes.data.network.controllers

import com.ase.weeshes.data.network.requests.NewCategoryRequest
import com.ase.weeshes.data.network.response.CategoriesResponse
import com.ase.weeshes.domain.model.Category
import com.ase.weeshes.domain.repository.CategoriesRepository
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.snapshots
import com.google.firebase.firestore.toObjects
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class CategoriesController @Inject constructor(private val firestore: FirebaseFirestore) : CategoriesRepository {

    companion object {
        const val USER_ID = "PeRi3uhriXGNItZWa2Mh"
        const val WEESHES = "weeshes"
        const val CATEGORIES = "categories"
    }

    override suspend fun createCategory(name: String, icon: String) {
        val ref = firestore.collection(WEESHES)
            .document(USER_ID)
            .collection(CATEGORIES)
            .document()

        ref.set(NewCategoryRequest(ref.id, name, icon)).isSuccessful
    }

    override suspend fun getCategories(): Flow<List<Category>> = firestore
        .collection(WEESHES)
        .document(USER_ID)
        .collection(CATEGORIES)
        .snapshots()
        .map {
            it.toObjects<CategoriesResponse>().map { w -> w.toDomain() }
        }
}