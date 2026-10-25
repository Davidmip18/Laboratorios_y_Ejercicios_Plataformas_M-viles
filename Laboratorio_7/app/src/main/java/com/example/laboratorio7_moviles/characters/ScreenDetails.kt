package com.example.laboratorio7_moviles.characters

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.laboratorio7_moviles.CharacterProfileViewModel
import com.example.laboratorio7_moviles.ErrorLayout
import com.example.laboratorio7_moviles.LoadingLayout

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterDetailScreen(
    onBackClick: () -> Unit,
    viewModel: CharacterProfileViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Character details") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) { Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back") }
                },
                actions = {
                    if (state.data != null) {
                        val char = state.data!!
                        IconButton(onClick = { viewModel.toggleFavorite(char.id, !char.isFavorite) }) {
                            Icon(
                                imageVector = if (char.isFavorite) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                                contentDescription = "Favorite"
                            )
                        }
                    }
                },
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
                state.hasError -> ErrorLayout(onRetry = { /* Retry logic */ })
                state.data != null -> {
                    val character = state.data!!
                    Column(
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        AsyncImage(
                            model = character.image,
                            contentDescription = character.name,
                            modifier = Modifier.size(200.dp).padding(bottom = 16.dp)
                        )
                        Text(text = character.name, style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(32.dp))

                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Species:", style = MaterialTheme.typography.bodyLarge)
                            Text(character.species, style = MaterialTheme.typography.bodyLarge)
                        }
                        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Status:", style = MaterialTheme.typography.bodyLarge)
                            Text(character.status, style = MaterialTheme.typography.bodyLarge)
                        }
                        Row(modifier = Modifier.fillMaxWidth().padding(top = 8.dp), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Gender:", style = MaterialTheme.typography.bodyLarge)
                            Text(character.gender, style = MaterialTheme.typography.bodyLarge)
                        }
                    }
                }
            }
        }
    }
}
