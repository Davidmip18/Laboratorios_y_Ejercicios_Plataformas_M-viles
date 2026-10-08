package com.example.laboratorio7_moviles.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.laboratorio7_moviles.ErrorLayout
import com.example.laboratorio7_moviles.LoadingLayout
import com.example.laboratorio7_moviles.LocationsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    onLocationClick: (Int) -> Unit,
    viewModel: LocationsViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Locations") },
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
                    LazyColumn(modifier = Modifier.fillMaxSize()) {
                        items(state.data!!) { location ->
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onLocationClick(location.id) }
                                    .padding(16.dp)
                            ) {
                                Text(text = location.name, style = MaterialTheme.typography.titleMedium)
                                Text(text = location.type, style = MaterialTheme.typography.bodySmall)
                            }
                            HorizontalDivider()
                        }
                    }
                }
            }
        }
    }
}
