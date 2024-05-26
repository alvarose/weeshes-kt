package com.ase.weeshes.domain.usecase.auth

import com.ase.weeshes.domain.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(private val repository: AuthRepository) {
    suspend operator fun invoke(name: String, email: String, pass: String) = repository.register(name, email, pass)
}