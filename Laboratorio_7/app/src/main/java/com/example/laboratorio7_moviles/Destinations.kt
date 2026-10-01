package com.example.laboratorio7_moviles

import kotlinx.serialization.Serializable

// Rutas principales
@Serializable object LoginDestination
@Serializable object MainGraph

// Rutas del Bottom Navigation (Nested Graphs)
@Serializable object CharactersGraph
@Serializable object LocationsGraph
@Serializable object ProfileDestination

// Pantallas individuales dentro de los grafos
@Serializable object CharactersListDestination
@Serializable data class CharacterDetailsDestination(val characterId: Int)

@Serializable object LocationsListDestination
@Serializable data class LocationDetailsDestination(val locationId: Int)