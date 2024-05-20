package com.ase.weeshes.data.network.controllers

import com.ase.weeshes.core.firebase.FirestoreValues
import com.ase.weeshes.data.network.requests.newCategoryRequest
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

    override suspend fun createCategory(name: String, icon: String): Boolean {
        val ref = firestore.collection(FirestoreValues.WEESHES_COLL)
            .document(FirestoreValues.USER_ID)
            .collection(FirestoreValues.CATEGORIES_COLL)
            .document()

        return ref.set(newCategoryRequest(ref.id, name, icon)).isSuccessful
    }

    override suspend fun deleteCategory(id: String) = firestore.collection(FirestoreValues.WEESHES_COLL)
        .document(FirestoreValues.USER_ID)
        .collection(FirestoreValues.CATEGORIES_COLL)
        .document(id).delete().isSuccessful

    override suspend fun getCategories(): Flow<List<Category>> = firestore
        .collection(FirestoreValues.WEESHES_COLL)
        .document(FirestoreValues.USER_ID)
        .collection(FirestoreValues.CATEGORIES_COLL)
        .snapshots()
        .map {
            it.toObjects<CategoriesResponse>().map { w -> w.toDomain() }
        }
}