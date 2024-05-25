package com.ase.weeshes.domain.repository

import com.google.firebase.auth.FirebaseUser

interface AuthRepository {
    suspend fun login(email: String, pass: String): FirebaseUser?
    suspend fun signUp(name: String, email: String, pass: String): FirebaseUser?
    fun isLoggedIn(): Boolean
}