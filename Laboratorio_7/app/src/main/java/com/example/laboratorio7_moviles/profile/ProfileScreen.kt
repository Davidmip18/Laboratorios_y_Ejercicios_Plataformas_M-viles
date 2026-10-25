package com.example.laboratorio7_moviles.profile

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.laboratorio7_moviles.AppDatabase
import com.example.laboratorio7_moviles.UserStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ProfileScreen(onLogout: () -> Unit) {
    val context = LocalContext.current
    val userStore = remember { UserStore(context) }
    val savedName by userStore.getName.collectAsState(initial = "")
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = Icons.Filled.AccountCircle,
            contentDescription = "Ícono de Perfil",
            modifier = Modifier.size(120.dp).padding(bottom = 16.dp),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(text = "Nombre: ${savedName ?: ""}", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                scope.launch(Dispatchers.IO) {
                    val db = AppDatabase.getDatabase(context)
                    db.characterDao().deleteAll()
                    db.locationDao().deleteAll()
                    userStore.deleteName()

                    launch(Dispatchers.Main) { onLogout() }
                }
            }
        ) {
            Text("Cerrar sesión")
        }
    }
}
