package com.ase.weeshes.domain.repository

interface RemoteConfigRepository {
    fun getAppVersion(): Int
    suspend fun getMinAllowedVersion(): Int
}