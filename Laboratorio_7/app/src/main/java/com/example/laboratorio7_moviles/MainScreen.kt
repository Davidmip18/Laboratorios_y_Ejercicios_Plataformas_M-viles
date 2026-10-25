package com.example.laboratorio7_moviles

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.*
import androidx.navigation.toRoute
import com.example.laboratorio7_moviles.characters.CharacterDetailScreen
import com.example.laboratorio7_moviles.characters.CharactersScreen
import com.example.laboratorio7_moviles.locations.LocationDetailsScreen
import com.example.laboratorio7_moviles.locations.LocationsScreen
import com.example.laboratorio7_moviles.profile.ProfileScreen

@Composable
fun MainScreen(rootNavController: NavController) {
    val bottomNavController = rememberNavController()

    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by bottomNavController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                NavigationBarItem(
                    icon = { Icon(Icons.Filled.Person, contentDescription = "Characters") },
                    label = { Text("Characters") },
                    selected = currentDestination?.hierarchy?.any { it.route?.contains("CharactersGraph") == true } == true,
                    onClick = {
                        bottomNavController.navigate(CharactersGraph) {
                            popUpTo(bottomNavController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.LocationOn, contentDescription = "Locations") },
                    label = { Text("Locations") },
                    selected = currentDestination?.hierarchy?.any { it.route?.contains("LocationsGraph") == true } == true,
                    onClick = {
                        bottomNavController.navigate(LocationsGraph) {
                            popUpTo(bottomNavController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
                NavigationBarItem(
                    icon = { Icon(Icons.Filled.AccountCircle, contentDescription = "Profile") },
                    label = { Text("Profile") },
                    selected = currentDestination?.hierarchy?.any { it.route?.contains("ProfileDestination") == true } == true,
                    onClick = {
                        bottomNavController.navigate(ProfileDestination) {
                            popUpTo(bottomNavController.graph.startDestinationId) { saveState = true }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                )
            }
        }
    ) { padding ->
        NavHost(
            navController = bottomNavController,
            startDestination = CharactersGraph,
            modifier = Modifier.padding(padding)
        ) {
            // GRAFO DE CHARACTERS
            navigation<CharactersGraph>(startDestination = CharactersListDestination) {
                composable<CharactersListDestination> {
                    CharactersScreen(
                        onCharacterClick = { id ->
                            bottomNavController.navigate(CharacterDetailsDestination(id))
                        }
                    )
                }
                composable<CharacterDetailsDestination> { backStackEntry ->
                    val destination = backStackEntry.toRoute<CharacterDetailsDestination>()
                    val viewModel: CharacterProfileViewModel = viewModel()

                    // Dispara la carga de datos en Room basándose en el ID recibido
                    LaunchedEffect(destination.characterId) {
                        viewModel.loadData(destination.characterId)
                    }

                    CharacterDetailScreen(
                        onBackClick = { bottomNavController.navigateUp() },
                        viewModel = viewModel
                    )
                }
            }

            // GRAFO DE LOCATIONS
            navigation<LocationsGraph>(startDestination = LocationsListDestination) {
                composable<LocationsListDestination> {
                    LocationsScreen(
                        onLocationClick = { id ->
                            bottomNavController.navigate(LocationDetailsDestination(id))
                        }
                    )
                }
                composable<LocationDetailsDestination> { backStackEntry ->
                    val destination = backStackEntry.toRoute<LocationDetailsDestination>()
                    val viewModel: LocationProfileViewModel = viewModel()

                    // Dispara la carga de datos en Room basándose en el ID recibido
                    LaunchedEffect(destination.locationId) {
                        viewModel.loadData(destination.locationId)
                    }

                    LocationDetailsScreen(
                        onNavigateBack = { bottomNavController.navigateUp() },
                        viewModel = viewModel
                    )
                }
            }

            // PANTALLA DE PERFIL
            composable<ProfileDestination> {
                ProfileScreen(onLogout = {
                    rootNavController.navigate(LoginDestination) {
                        popUpTo(0) { inclusive = true }
                    }
                })
            }
        }
    }
}
