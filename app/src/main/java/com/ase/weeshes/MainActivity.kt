package com.ase.weeshes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.ase.weeshes.ui.navigation.NavigationGraph
import com.ase.weeshes.ui.screens.splash.SplashViewModel
import com.ase.weeshes.ui.theme.WeeshesTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val splashViewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                splashViewModel.isSplashShow.value
            }
        }

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            WeeshesTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    NavigationGraph(splashViewModel)
                }
            }
        }
    }
}