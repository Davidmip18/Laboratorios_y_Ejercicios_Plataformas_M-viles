package com.example.laboratorio7_moviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.laboratorio7_moviles.login.ScreenLogin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val rootNavController = rememberNavController()

                NavHost(
                    navController = rootNavController,
                    startDestination = LoginDestination
                ) {

                    composable<LoginDestination> {
                        ScreenLogin(
                            onLoginSuccess = {
                                rootNavController.navigate(MainGraph) {
                                    // Limpiamos la pila para que la app se cierre al dar Back
                                    popUpTo(LoginDestination) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable<MainGraph> {
                        MainScreen(rootNavController = rootNavController)
                    }
                }
            }
        }
    }
}
