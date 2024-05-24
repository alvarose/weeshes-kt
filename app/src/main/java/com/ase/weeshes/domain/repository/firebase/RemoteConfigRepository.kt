package com.ase.weeshes.domain.repository.firebase

interface RemoteConfigRepository {
    fun getAppVersion(): Int
    suspend fun getMinAllowedVersion(): Int
}