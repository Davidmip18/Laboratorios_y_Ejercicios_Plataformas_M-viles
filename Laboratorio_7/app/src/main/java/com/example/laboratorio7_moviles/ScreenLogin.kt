package com.example.laboratorio7_moviles

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun LoginScreen(onNavigateToCharacters: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        // Logo de la app
        Image(
            painter = painterResource(id = R.drawable.rick_and_morty_logo),
            contentDescription = "Rick and Morty Logo",
            modifier = Modifier.padding(32.dp)
        )

        Button(
            onClick = onNavigateToCharacters,
            colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
        ) {
            Text("Entrar")
        }

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "David Ibaté - 251732",
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(bottom = 32.dp)
        )
    }
}
