package com.ase.weeshes.ui.components.ui

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.ase.weeshes.ui.theme.Typography

@Composable
fun TitleLarge(@StringRes text: Int) = TitleLarge(stringResource(text))

@Composable
fun TitleLarge(text: String) {
    Text(
        text = text, style = Typography.titleLarge,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}