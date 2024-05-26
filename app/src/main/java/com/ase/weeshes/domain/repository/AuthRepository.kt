package com.ase.weeshes.domain.repository

import com.ase.weeshes.data.network.response.Response
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, pass: String): Flow<Response<AuthResult>>
    suspend fun register(name: String, email: String, pass: String): Flow<Response<AuthResult>>
    suspend fun loginGoogle(token: String): FirebaseUser?
    suspend fun logout()

    suspend fun userUid(): String

    suspend fun isLoggedIn(): Boolean
}