package com.example.laboratorio6_moviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Estructura para guardar cada movimiento del historial
data class Movimiento(val valor: Int, val esIncremento: Boolean)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ContadorAvanzadoApp()
                }
            }
        }
    }
}

@Composable
fun ContadorAvanzadoApp() {
    // Manejo de Estados
    var contador by remember { mutableStateOf(0) }
    var totalIncrementos by remember { mutableStateOf(0) }
    var totalDecrementos by remember { mutableStateOf(0) }
    var valorMaximo by remember { mutableStateOf(0) }
    var valorMinimo by remember { mutableStateOf(0) }
    var totalCambios by remember { mutableStateOf(0) }

    // Lista para el historial
    var historial by remember { mutableStateOf(listOf<Movimiento>()) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Título
        Text(
            text = "David Ibaté",
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // 2. Contador
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Botón de decremento (-)
            IconButton(
                onClick = {
                    contador--
                    totalDecrementos++
                    totalCambios++
                    valorMinimo = minOf(valorMinimo, contador)
                    historial = historial + Movimiento(contador, false)
                },
                modifier = Modifier.background(Color(0xFF3F51B5), CircleShape)
            ) {
                Text("-", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }

            // Número central
            Text(text = contador.toString(), fontSize = 48.sp)

            // Botón de incremento (+)
            IconButton(
                onClick = {
                    contador++
                    totalIncrementos++
                    totalCambios++
                    valorMaximo = maxOf(valorMaximo, contador)
                    historial = historial + Movimiento(contador, true)
                },
                modifier = Modifier.background(Color(0xFF3F51B5), CircleShape)
            ) {
                Text("+", color = Color.White, fontSize = 24.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Divider(color = Color.LightGray, thickness = 1.dp)
        Spacer(modifier = Modifier.height(16.dp))

        // 3. Estadísticas
        Column(modifier = Modifier.fillMaxWidth()) {
            FilaEstadistica("Total incrementos:", totalIncrementos.toString())
            FilaEstadistica("Total decrementos:", totalDecrementos.toString())
            FilaEstadistica("Valor máximo:", valorMaximo.toString())
            FilaEstadistica("Valor mínimo:", valorMinimo.toString())
            FilaEstadistica("Total cambios:", totalCambios.toString())
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Historial:",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 18.sp
        )
        Spacer(modifier = Modifier.height(8.dp))

        // 4. Historial
        LazyVerticalGrid(
            columns = GridCells.Fixed(5),
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(historial) { movimiento ->
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .background(
                            // Verde si es incremento, Rojo si es decremento
                            color = if (movimiento.esIncremento) Color(0xFF2E7D32) else Color(0xFFC62828),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .padding(8.dp)
                ) {
                    Text(
                        text = movimiento.valor.toString(),
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }

        // 5. Botón de Reiniciar
        Button(
            onClick = {
                // Volver todos los estados a 0 y limpiar lista
                contador = 0
                totalIncrementos = 0
                totalDecrementos = 0
                valorMaximo = 0
                valorMinimo = 0
                totalCambios = 0
                historial = emptyList()
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF3F51B5))
        ) {
            Text("Reiniciar", color = Color.White)
        }
    }
}

// Función auxiliar (Composable) para crear las filas de texto de las estadísticas
@Composable
fun FilaEstadistica(etiqueta: String, valor: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(text = etiqueta, fontWeight = FontWeight.Bold, fontSize = 16.sp)
        Text(text = valor, fontSize = 16.sp)
    }
}
