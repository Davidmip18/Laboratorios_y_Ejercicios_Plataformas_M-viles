package com.example.laboratorio4_moviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FrontendBasicoScreen()
                }
            }
        }
    }
}

@Composable
fun FrontendBasicoScreen() {
    // Box principal para el borde y el fondo
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            // Requerimiento: Borde verde
            .border(width = 4.dp, color = Color(0xFF1B5E20))
            .padding(24.dp), // Espaciado interno para que el texto no se pegue al borde
        contentAlignment = Alignment.Center
    ) {

        // Requerimiento: Escudo UVG de fondo
        Image(
            painter = painterResource(id = R.drawable.uvg_logo),
            contentDescription = "Escudo UVG fondo",
            modifier = Modifier
                .size(300.dp)
                .alpha(0.15f), // Esto le da el efecto de marca de agua transparente
            contentScale = ContentScale.Fit
        )

        // Contenedor para alinear los textos verticalmente
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Universidad del Valle\nde Guatemala",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Programación de plataformas\nmóviles, Sección 30",
                fontSize = 20.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            // Fila para INTEGRANTES
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "INTEGRANTES",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                // Columna para alinear los nombres a la derecha
                Column(horizontalAlignment = Alignment.End) {
                    Text(text = "Rodrigo Rivera")
                    Text(text = "David Ibaté")
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Fila para CATEDRÁTICO
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "CATEDRÁTICO",
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(text = "Juan Carlos Durini")
            }

            // Usamos un Spacer con weight(1f) para empujar los últimos textos hasta abajo
            Spacer(modifier = Modifier.weight(1f))

            // Datos del alumno (yo) en la parte inferior
            Text(
                text = "David Magdaleno Ibaté Pérez",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )
            Text(
                text = "251732",
                fontSize = 16.sp,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
