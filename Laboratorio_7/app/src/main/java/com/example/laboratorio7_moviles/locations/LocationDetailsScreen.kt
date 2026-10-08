package com.example.laboratorio7_moviles.locations

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.laboratorio7_moviles.ErrorLayout
import com.example.laboratorio7_moviles.LoadingLayout
import com.example.laboratorio7_moviles.LocationProfileViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationDetailsScreen(
    onNavigateBack: () -> Unit,
    viewModel: LocationProfileViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Location Details") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { padding ->
        Box(modifier = Modifier.padding(padding).fillMaxSize()) {
            when {
                state.isLoading -> {
                    LoadingLayout(onErrorClick = { viewModel.forceError() })
                }
                state.hasError -> {
                    ErrorLayout(onRetry = { viewModel.loadData() })
                }
                state.data != null -> {
                    val location = state.data!!
                    Column(
                        modifier = Modifier.padding(16.dp).fillMaxWidth()
                    ) {
                        Text(text = location.name, style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(16.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("ID:")
                            Text("${location.id}")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Type:")
                            Text(location.type)
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                            Text("Dimension:")
                            Text(location.dimension)
                        }
                    }
                }
            }
        }
    }
}
