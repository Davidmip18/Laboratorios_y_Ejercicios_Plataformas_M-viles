package com.example.laboratorio7_moviles.characters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.laboratorio7_moviles.CharactersViewModel
import com.example.laboratorio7_moviles.ErrorLayout
import com.example.laboratorio7_moviles.LoadingLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharactersScreen(
    onCharacterClick: (Int) -> Unit,
    viewModel: CharactersViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    var showOnlyFavorites by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Characters") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            when {
                state.isLoading -> LoadingLayout(onErrorClick = { viewModel.forceError() })
                state.hasError -> ErrorLayout(onRetry = { viewModel.loadData() })
                state.data != null -> {
                    val chars = state.data!!
                    val filteredChars = if (showOnlyFavorites) chars.filter { it.isFavorite } else chars

                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Mostrar solo favoritos", style = MaterialTheme.typography.titleMedium)
                            Switch(checked = showOnlyFavorites, onCheckedChange = { showOnlyFavorites = it })
                        }

                        if (filteredChars.isEmpty()) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("No hay elementos a mostrar")
                            }
                        } else {
                            LazyColumn(modifier = Modifier.fillMaxSize()) {
                                items(filteredChars) { character ->
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .clickable { onCharacterClick(character.id) }
                                            .padding(16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        AsyncImage(
                                            model = character.image,
                                            contentDescription = character.name,
                                            modifier = Modifier.size(64.dp)
                                        )
                                        Spacer(modifier = Modifier.width(16.dp))
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text(text = character.name, style = MaterialTheme.typography.titleMedium)
                                            Text(text = "${character.species} - ${character.status}", style = MaterialTheme.typography.bodyMedium)
                                        }
                                        IconButton(onClick = { viewModel.toggleFavorite(character.id, !character.isFavorite) }) {
                                            Icon(
                                                imageVector = if (character.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                                contentDescription = "Favorite"
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
