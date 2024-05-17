package com.ase.weeshes

import android.app.Application
import android.content.Context
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeeshesApp : Application() {
    init {
        instance = this
    }

    private lateinit var analytics: FirebaseAnalytics

    companion object {
        private var instance: WeeshesApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }

    override fun onCreate() {
        super.onCreate()

        analytics = Firebase.analytics

        // val context: Context = applicationContext()
    }
}