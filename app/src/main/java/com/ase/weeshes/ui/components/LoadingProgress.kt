package com.ase.weeshes.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ase.weeshes.ui.theme.FontColor
import com.ase.weeshes.ui.theme.FontColorDark

@Composable
@Preview
fun LoadingProgress() {
    Box(modifier = Modifier.fillMaxSize().background(FontColor.copy(alpha = 0.5f))) {
        CircularProgressIndicator(
            modifier = Modifier
                .width(64.dp)
                .align(Alignment.Center),
            color = FontColorDark,
            trackColor = FontColor,
        )
    }
}