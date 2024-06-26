package com.ase.weeshes.ui.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.ase.weeshes.R
import com.ase.weeshes.core.ex.toast
import com.ase.weeshes.domain.model.Category
import com.ase.weeshes.ui.components.nav.MainScaffold
import com.ase.weeshes.ui.components.nav.TopBarTitle
import com.ase.weeshes.ui.components.ui.TitleLarge
import com.ase.weeshes.ui.theme.FontColor
import com.ase.weeshes.ui.theme.LightColor
import com.ase.weeshes.ui.theme.LightRed

@Composable
fun CategoriesScreen(
    viewModel: CategoriesViewModel = hiltViewModel(),
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsState()
    val listState = rememberLazyListState()

    var showAddDialog by remember { mutableStateOf(false) }

    var categoryToDelete by remember { mutableStateOf<Category?>(null) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    AddCategoryDialog(showAddDialog,
        onSaveClick = { name, icon ->
            viewModel.addCategory(name, icon)
            showAddDialog = false
        }
    ) { showAddDialog = false }

    categoryToDelete?.let { category ->
        DeleteCategoryDialog(showDeleteDialog,
            category,
            onDeleteClick = { id ->
                viewModel.deleteCategory(id)
                showDeleteDialog = false
            }
        ) { showDeleteDialog = false }
    }

    MainScaffold(
        title = TopBarTitle.Resource(R.string.title_categories),
        navIcon = {
            IconButton(onClick = { onBack() }) {
                Icon(imageVector = Icons.AutoMirrored.Rounded.ArrowBack, contentDescription = null)
            }
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(imageVector = Icons.Rounded.Add, tint = FontColor, contentDescription = null)
            }
        }
    ) { padding ->
        Column(Modifier.padding(horizontal = 16.dp, vertical = (padding.calculateTopPadding() + 16.dp))) {
            LazyColumn(
                state = listState,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.White)
                    .border(1.dp, LightColor, RoundedCornerShape(12.dp))
            ) {
                items(uiState.categories, key = { c -> c.id }) { category ->
                    CategoryView(category) {
                        categoryToDelete = category
                        showDeleteDialog = true
                    }
                    HorizontalDivider(thickness = 1.dp, color = LightColor)
                }
            }
        }
    }
}

@Composable
private fun CategoryView(
    category: Category,
    onDeleteClick: (Category) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
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
        IconButton(onClick = { onDeleteClick(category) }) {
            Icon(imageVector = Icons.Rounded.Delete, contentDescription = null, tint = LightRed)
        }
    }
}

@Composable
private fun AddCategoryDialog(
    showDialog: Boolean,
    onSaveClick: (String, String) -> Unit,
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
                    TitleLarge(text = "Nueva categoría")

                    var name by remember { mutableStateOf("") }
                    var icon by remember { mutableStateOf("\uD83D\uDC92") }

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(16.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            value = name,
                            onValueChange = { newText ->
                                name = newText.trim()
                            },
                            placeholder = { Text(text = "Nombre") },
                            modifier = Modifier.weight(2f)
                        )

                        TextField(
                            value = icon,
                            onValueChange = { emoji ->
                                if (icon.isEmpty() && emoji.isNotEmpty()) {
                                    val filter = emoji.filter { Character.getType(it) == 19 }
                                    icon = filter.ifEmpty { "" }
                                } else if (emoji.isEmpty()) icon = ""
                            },
                            maxLines = 1,
                            textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                            placeholder = { Text(text = "Icon") },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Button(onClick = { onSaveClick(name, icon) }) {
                        Text(text = "Guardar")
                    }
                }
            }
        }
    }
}

@Composable
private fun DeleteCategoryDialog(
    showDialog: Boolean,
    category: Category,
    onDeleteClick: (String) -> Unit,
    onDismissRequest: () -> Unit,
) {
    if (showDialog) {
        AlertDialog(
            title = {
                Text(text = "${category.icon} ${category.name}", textAlign = TextAlign.Start, modifier = Modifier.fillMaxWidth())
            },
            text = {
                Text(text = "¿Estás seguro de eliminar esta categoría?")
            },
            onDismissRequest = {
                onDismissRequest()
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDeleteClick(category.id)
                    }
                ) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        onDismissRequest()
                    }
                ) {
                    Text("Cancelar")
                }
            }
        )
    }
}
