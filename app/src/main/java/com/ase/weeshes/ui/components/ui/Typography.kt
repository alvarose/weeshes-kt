package com.ase.weeshes.ui.components.ui

import androidx.annotation.StringRes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.ase.weeshes.ui.theme.Typography

@Composable
fun TitleLarge(@StringRes text: Int, modifier: Modifier = Modifier) = TitleLarge(stringResource(text), modifier)

@Composable
fun TitleLarge(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text, style = Typography.titleLarge,
        modifier = modifier
    )
}