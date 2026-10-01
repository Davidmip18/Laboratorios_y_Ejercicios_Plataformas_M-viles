package com.example.laboratorio7_moviles.locations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.laboratorio7_moviles.LocationDb

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LocationsScreen(
    locationDb: LocationDb,
    onLocationClick: (Int) -> Unit
) {
    val locations = locationDb.getAllLocations()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Locations") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primaryContainer)
            )
        }
    ) { padding ->
        LazyColumn(contentPadding = padding) {
            items(locations) { location ->
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
