package com.ase.weeshes.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ase.weeshes.R
import com.ase.weeshes.domain.model.Wishlist
import com.ase.weeshes.ui.components.ui.TitleLarge
import com.ase.weeshes.ui.theme.FontColor
import com.ase.weeshes.ui.theme.LightColor

@Composable
fun HomeScreen(
    viewModel: HomeScreenViewModel = hiltViewModel(),
    onWishlistClick: (Wishlist) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        TitleLarge(text = R.string.my_weeshes)
        uiState.wishlists.forEach { wishlist ->
            WheeshesView(wishlist) { onWishlistClick(wishlist) }
        }
    }
}

@Composable
private fun WheeshesView(
    wishlist: Wishlist,
    onWishlistClick: (Wishlist) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable { onWishlistClick(wishlist) }
            .border(1.dp, LightColor, RoundedCornerShape(12.dp))
            .padding(16.dp)
    ) {
        Text(
            text = wishlist.icon, style = TextStyle(
                fontSize = 20.sp
            )
        )
        Text(
            text = wishlist.name, style = TextStyle(
                color = FontColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            ), modifier = Modifier.weight(1f)
        )
        Icon(imageVector = Icons.AutoMirrored.Rounded.KeyboardArrowRight, tint = FontColor, contentDescription = null)
    }
}