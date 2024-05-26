package com.ase.weeshes.domain.usecase.auth

import com.ase.weeshes.domain.repository.AuthRepository
import javax.inject.Inject

class LogoutUseCase @Inject constructor(private val authenticationRepository: AuthRepository) {
    suspend operator fun invoke() = authenticationRepository.logout()
}