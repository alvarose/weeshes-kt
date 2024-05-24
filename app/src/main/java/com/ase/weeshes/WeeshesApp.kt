package com.ase.weeshes

import android.app.Application
import android.content.Context
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeeshesApp : Application() {
    init {
        instance = this
    }

    companion object {
        private var instance: WeeshesApp? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }
    }
}