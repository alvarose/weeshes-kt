package com.ase.weeshes.domain.usecase.auth

import com.ase.weeshes.core.ex.toLog
import com.ase.weeshes.domain.repository.RemoteConfigRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CanAccessUseCase @Inject constructor(private val repository: RemoteConfigRepository) {
    suspend operator fun invoke(): Flow<Boolean> = flow {
        val appVersion = repository.getAppVersion()
        val minVersion = repository.getMinAllowedVersion()

        emit(appVersion >= minVersion)
    }
}