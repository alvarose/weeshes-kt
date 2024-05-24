package com.ase.weeshes.ui.screens.wishlist

import android.content.Intent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.KeyboardArrowUp
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.ase.weeshes.R
import com.ase.weeshes.domain.model.Category
import com.ase.weeshes.domain.model.Product
import com.ase.weeshes.domain.model.Wishlist
import com.ase.weeshes.ui.components.nav.MainScaffold
import com.ase.weeshes.ui.components.nav.TopBarTitle
import com.ase.weeshes.ui.components.ui.TitleLarge
import com.ase.weeshes.ui.theme.FontColor
import com.ase.weeshes.ui.theme.FontColorDark
import com.ase.weeshes.ui.theme.LightColor

@Composable
fun WishlistScreen(
    viewModel: WishlistViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    var showDialog by remember { mutableStateOf(false) }

    uiState.wishlist?.let { wishlist ->
        AddProductDialog(showDialog, uiState.categories,
            onSaveClick = { name, link, category ->
                viewModel.addProduct(wishlist.id, name, link, category)
                showDialog = false
            }
        ) { showDialog = false }

        MainScaffold(
            title = TopBarTitle.Text("${wishlist.icon} ${wishlist.name}"),
            navIcon = {
                IconButton(onClick = { onBack() }) {
                    Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
                }
            },
            actions = {
                var expanded by remember { mutableStateOf(false) }

                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                    modifier = Modifier.padding(horizontal = 16.dp)
                ) {
                    DropdownMenuItem(text = { Text(text = "Editar") }, onClick = {})
                    DropdownMenuItem(text = { Text(text = "Eliminar") }, onClick = {})
                }
            },
            floatingActionButton = {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    SmallFloatingActionButton(
                        onClick = {
                            context.startActivity(Intent.createChooser(Intent().apply {
                                action = Intent.ACTION_SEND
                                val shareText: String = String.format(context.getString(R.string.wishlist_share_text), wishlist.id)
                                putExtra(Intent.EXTRA_TEXT, shareText)
                                type = "text/plain"
                            }, null))
                        },
                        contentColor = FontColorDark,
                        elevation = FloatingActionButtonDefaults.elevation(
                            defaultElevation = 2.dp
                        )
                    ) {
                        Icon(imageVector = Icons.Rounded.Share, contentDescription = null)
                    }
                    FloatingActionButton(
                        onClick = { showDialog = true },
                        contentColor = FontColorDark,
                        elevation = FloatingActionButtonDefaults.elevation(
                            defaultElevation = 3.dp
                        )
                    ) {
                        Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
                    }
                }
            }
        ) { padding ->
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
            ) {
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
            if (categoryId.isNotEmpty()) {
                SectionTitle(categories.find { it.id == categoryId }!!, isExpanded = isExpanded) {
                    isExpanded = !isExpanded
                }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AddProductDialog(
    showDialog: Boolean,
    categories: List<Category>,
    onSaveClick: (String, String, String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    if (showDialog) {
        Dialog(
            onDismissRequest = onDismissRequest,
            properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .pointerInput(Unit) { detectTapGestures { } }
                        .shadow(8.dp, shape = RoundedCornerShape(16.dp))
                        .clip(RoundedCornerShape(16.dp))
                        .background(MaterialTheme.colorScheme.surface)
                        .padding(16.dp)
                ) {
                    TitleLarge(text = "Añadir producto")

                    var name by remember { mutableStateOf("") }
                    var link by remember { mutableStateOf("") }
                    var category by remember { mutableStateOf("") }

                    var selectedCategory by remember { mutableStateOf(categories[0].icon + " " + categories[0].name) }
                    var expanded by remember { mutableStateOf(false) }

                    TextField(
                        value = name,
                        onValueChange = { newName ->
                            name = newName.trim()
                        },
                        placeholder = { Text(text = "Nombre") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    TextField(
                        value = link,
                        onValueChange = { newLink ->
                            link = newLink.trim()
                        },
                        placeholder = { Text(text = "Link") },
                        modifier = Modifier.fillMaxWidth()
                    )

                    ExposedDropdownMenuBox(
                        expanded = expanded,
                        onExpandedChange = {
                            expanded = !expanded
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = selectedCategory,
                            onValueChange = {},
                            readOnly = true,
                            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor()
                        )

                        ExposedDropdownMenu(
                            expanded = expanded,
                            onDismissRequest = { expanded = false }
                        ) {
                            categories.forEach { item ->
                                DropdownMenuItem(
                                    text = { Text(text = item.icon + " " + item.name) },
                                    onClick = {
                                        selectedCategory = item.icon + " " + item.name
                                        category = item.id
                                        expanded = false
                                    },
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                        }
                    }

                    Button(onClick = { onSaveClick(name, link, category) }, modifier = Modifier.fillMaxWidth()) {
                        Text(text = "Añadir")
                    }
                }
            }
        }
    }
}