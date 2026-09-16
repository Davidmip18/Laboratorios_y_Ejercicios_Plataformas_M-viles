package com.example.laboratorio7_moviles

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = LoginScreenRoute) {

                    composable<LoginScreenRoute> {
                        LoginScreen(
                            onNavigateToCharacters = {
                                navController.navigate(CharactersScreenRoute) {
                                    // Esto elimina el Login del BackStack
                                    popUpTo(LoginScreenRoute) { inclusive = true }
                                }
                            }
                        )
                    }

                    composable<CharactersScreenRoute> {
                        CharactersScreen(
                            onCharacterClick = { id ->
                                navController.navigate(CharacterDetailScreenRoute(characterId = id))
                            }
                        )
                    }

                    composable<CharacterDetailScreenRoute> { backStackEntry ->
                        // Se extrae el argumento usando Type-Safe Navigation
                        val route = backStackEntry.toRoute<CharacterDetailScreenRoute>()

                        CharacterDetailScreen(
                            characterId = route.characterId,
                            onBackClick = { navController.popBackStack() }
                        )
                    }
                }
            }
        }
    }
}
