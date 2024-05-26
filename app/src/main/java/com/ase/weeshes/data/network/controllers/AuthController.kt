package com.ase.weeshes.data.network.controllers

import com.ase.weeshes.data.network.response.Response
import com.ase.weeshes.domain.repository.AuthRepository
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.tasks.await
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class AuthController @Inject constructor(private val auth: FirebaseAuth) : AuthRepository {

    override suspend fun userUid(): String = auth.currentUser?.uid ?: ""

    override suspend fun isLoggedIn(): Boolean = auth.currentUser == null

    override suspend fun logout() = auth.signOut()

    override suspend fun login(email: String, pass: String): Flow<Response<AuthResult>> = flow {
        try {
            emit(Response.Loading)
            val result = auth.signInWithEmailAndPassword(email, pass).await()
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Oops, something went wrong."))
        }
    }

    override suspend fun register(name: String, email: String, pass: String): Flow<Response<AuthResult>> = flow {
        try {
            emit(Response.Loading)
            val result = auth.createUserWithEmailAndPassword(email, pass).await()
            emit(Response.Success(result))
        } catch (e: Exception) {
            emit(Response.Error(e.localizedMessage ?: "Oops, something went wrong."))
        }
    }

    override suspend fun loginGoogle(token: String): FirebaseUser? {
//        val gso = GoogleSignInOptions
//            .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(context.getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()

        val credential = GoogleAuthProvider.getCredential(token, null)
        return completeSignUpWithCredential(credential)
    }

    private suspend fun completeSignUpWithCredential(credential: AuthCredential): FirebaseUser? {
        return suspendCancellableCoroutine { cancellableContinuation ->
            auth.signInWithCredential(credential)
                .addOnSuccessListener {
                    cancellableContinuation.resume(it.user)
                }
                .addOnFailureListener {
                    cancellableContinuation.resumeWithException(it)
                }
        }
    }
}