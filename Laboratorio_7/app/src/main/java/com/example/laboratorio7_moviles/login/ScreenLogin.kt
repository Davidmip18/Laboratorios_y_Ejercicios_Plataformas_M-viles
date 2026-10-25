package com.example.laboratorio7_moviles.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.laboratorio7_moviles.AppDatabase
import com.example.laboratorio7_moviles.CharacterDb
import com.example.laboratorio7_moviles.LocationDb
import com.example.laboratorio7_moviles.R
import com.example.laboratorio7_moviles.UserStore
import com.example.laboratorio7_moviles.toEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@Composable
fun ScreenLogin(onLoginSuccess: () -> Unit) {
    val context = LocalContext.current
    val userStore = remember { UserStore(context) }

    val savedName by userStore.getName.collectAsState(initial = null)
    var nameInput by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(savedName) {
        if (!savedName.isNullOrBlank()) {
            onLoginSuccess()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(id = R.drawable.rick_and_morty_logo),
            contentDescription = "Logo Rick and Morty",
            modifier = Modifier.fillMaxWidth(0.8f).padding(bottom = 32.dp)
        )

        TextField(
            value = nameInput,
            onValueChange = { nameInput = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth(0.8f),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = {
                    if (nameInput.isNotBlank()) {
                        isLoading = true
                        scope.launch(Dispatchers.IO) {
                            val db = AppDatabase.getDatabase(context)
                            val chars = db.characterDao().getAllCharacters().first()

                            // Sincronización inicial
                            if (chars.isEmpty()) {
                                delay(4000)
                                db.characterDao().insertAll(CharacterDb().getAllCharacters().map { it.toEntity() })
                                db.locationDao().insertAll(LocationDb().getAllLocations().map { it.toEntity() })
                            }

                            userStore.saveName(nameInput)
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth(0.8f)
            ) {
                Text("Iniciar sesión")
            }
        }
    }
}
