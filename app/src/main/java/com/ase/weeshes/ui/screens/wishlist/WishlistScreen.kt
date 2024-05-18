package com.ase.weeshes.ui.screens.wishlist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.automirrored.rounded.ExitToApp
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ase.weeshes.domain.model.Category
import com.ase.weeshes.domain.model.Product
import com.ase.weeshes.domain.model.Wishlist
import com.ase.weeshes.ui.components.nav.MainScaffold
import com.ase.weeshes.ui.components.nav.TopBarTitle
import com.ase.weeshes.ui.theme.FontColor
import com.ase.weeshes.ui.theme.LightColor

@Composable
fun WishlistScreen(
    viewModel: WishlistViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()

    MainScaffold(
        title = TopBarTitle.Text(uiState.wishlist?.let { "${it.icon} ${it.name}" } ?: ""),
        navIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
            }
        }
    ) { padding ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            uiState.wishlist?.let { wishlist ->
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    WishlistView(wishlist, uiState.categories)
                }
            }
        }
    }
}

@Composable
private fun WishlistView(
    wishlist: Wishlist,
    categories: List<Category>,
) {
    val sections = wishlist.products.groupBy { it.category }.toList()

    sections.forEach { (categoryId, products) ->
        var isExpanded by remember { mutableStateOf(true) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color.White)
                .border(1.dp, LightColor, RoundedCornerShape(12.dp))
        ) {
            SectionTitle(categories.find { it.id == categoryId }!!, isExpanded = isExpanded) {
                isExpanded = !isExpanded
            }

            if (isExpanded) {
                products.forEach { product -> ProductItem(product) }
            }
        }
    }
}

@Composable
private fun SectionTitle(
    category: Category,
    isExpanded: Boolean = false,
    onHeaderClicked: () -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(if (isExpanded) LightColor else Color.White)
            .clickable { onHeaderClicked() }
            .padding(16.dp)
    ) {
        Text(
            text = category.icon, style = TextStyle(
                fontSize = 20.sp
            )
        )
        Text(
            text = category.name, style = TextStyle(
                color = FontColor,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            ), modifier = Modifier.weight(1f)
        )
        Icon(imageVector = if (isExpanded) Icons.Rounded.KeyboardArrowUp else Icons.Rounded.KeyboardArrowDown, tint = FontColor, modifier = Modifier.size(18.dp), contentDescription = null)
    }
}

@Composable
private fun ProductItem(
    product: Product,
) {
    val uriHandler = LocalUriHandler.current

    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable {
                uriHandler.openUri(product.link)
            }
            .padding(16.dp)
    ) {
        Text(
            text = "\uD83D\uDD17", style = TextStyle(
                fontSize = 16.sp
            )
        )
        Text(
            text = product.name, style = TextStyle(
                color = FontColor,
                fontSize = 16.sp
            ), modifier = Modifier.weight(1f)
        )
        Icon(imageVector = Icons.AutoMirrored.Rounded.ExitToApp, tint = LightColor, modifier = Modifier.size(18.dp), contentDescription = null)
    }
}

@Composable
@Preview(showBackground = true)
fun WishlistScreenPreview() {
    WishlistScreen {}
}