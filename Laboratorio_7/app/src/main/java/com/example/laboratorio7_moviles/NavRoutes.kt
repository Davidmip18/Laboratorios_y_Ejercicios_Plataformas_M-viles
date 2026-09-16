package com.example.laboratorio7_moviles

import kotlinx.serialization.Serializable

@Serializable
object LoginScreenRoute

@Serializable
object CharactersScreenRoute

@Serializable
data class CharacterDetailScreenRoute(val characterId: Int) // Solo se manda el ID, NADA MÁS.