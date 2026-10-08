package com.example.laboratorio7_moviles.characters

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
                state.isLoading -> {
                    LoadingLayout(onErrorClick = { viewModel.forceError() })
                }
                state.hasError -> {
                    ErrorLayout(onRetry = { viewModel.loadData() })
                }
                state.data != null -> {
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.data!!) { character ->
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
                                Column {
                                    Text(text = character.name, style = MaterialTheme.typography.titleMedium)
                                    Text(
                                        text = "${character.species} - ${character.status}",
                                        style = MaterialTheme.typography.bodyMedium
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
