package com.ase.weeshes.ui.components.nav

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.ase.weeshes.ui.components.ui.TitleLarge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScaffold(
    title: TopBarTitle,
    actions: @Composable () -> Unit = {},
    navIcon: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    content: @Composable (PaddingValues) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    when (title) {
                        is TopBarTitle.Resource -> TitleLarge(title.resId)
                        is TopBarTitle.Text -> TitleLarge(title.text)
                    }
                },
                navigationIcon = { navIcon() },
                actions = { actions() },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                )
            )
        },
        floatingActionButton = { floatingActionButton() }
    ) { paddingValues ->
        content(paddingValues)
    }
}

sealed class TopBarTitle {
    data class Text(val text: String) : TopBarTitle()
    data class Resource(@StringRes val resId: Int) : TopBarTitle()
}