package com.ase.weeshes.data.network.controllers

import com.ase.weeshes.domain.repository.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class AuthController @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {
    override suspend fun login(email: String, pass: String): FirebaseUser? = auth.signInWithEmailAndPassword(email, pass).await().user
    override suspend fun signUp(name: String, email: String, pass: String): FirebaseUser? = auth.createUserWithEmailAndPassword(email, pass).await().user
}