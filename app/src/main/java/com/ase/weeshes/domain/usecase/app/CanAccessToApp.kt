package com.ase.weeshes.domain.usecase.app

import com.ase.weeshes.core.ex.toLog
import com.ase.weeshes.domain.repository.RemoteConfigRepository
import javax.inject.Inject

class CanAccessToApp @Inject constructor(private val repository: RemoteConfigRepository) {
    suspend operator fun invoke(): Boolean {
        val appVersion = repository.getAppVersion()
        val minVersion = repository.getMinAllowedVersion()

        "$appVersion $minVersion".toLog()

        return appVersion >= minVersion
    }
}