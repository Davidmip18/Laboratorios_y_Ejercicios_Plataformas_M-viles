package com.example.laboratorio7_moviles

import androidx.room.Entity
import androidx.room.PrimaryKey

// 1. Entities
@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val status: String,
    val species: String,
    val gender: String,
    val image: String,
    val isFavorite: Boolean = false
)

@Entity(tableName = "locations")
data class LocationEntity(
    @PrimaryKey val id: Int,
    val name: String,
    val type: String,
    val dimension: String
)

// 2. Mappers
fun CharacterEntity.toModel(): Character {
    return Character(id, name, status, species, gender, image, isFavorite)
}

fun Character.toEntity(): CharacterEntity {
    return CharacterEntity(id, name, status, species, gender, image, isFavorite)
}

fun LocationEntity.toModel(): Location {
    return Location(id, name, type, dimension)
}

fun Location.toEntity(): LocationEntity {
    return LocationEntity(id, name, type, dimension)
}
