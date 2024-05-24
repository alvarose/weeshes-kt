package com.ase.weeshes.data.network.controllers.firebase

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import com.ase.weeshes.core.firebase.RemoteConfigValues
import com.ase.weeshes.domain.repository.firebase.RemoteConfigRepository
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class RemoteConfigController @Inject constructor(
    private val remoteConfig: FirebaseRemoteConfig,
    private val context: Context,
) : RemoteConfigRepository {
    @RequiresApi(Build.VERSION_CODES.P)
    override fun getAppVersion(): Int {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        val appVersion = packageInfo.longVersionCode

        return appVersion.toInt()
    }

    override suspend fun getMinAllowedVersion(): Int {
        remoteConfig.fetch(0)
        remoteConfig.activate().await()

        return remoteConfig.getString(RemoteConfigValues.VERSION_KEY).toInt()
    }

}