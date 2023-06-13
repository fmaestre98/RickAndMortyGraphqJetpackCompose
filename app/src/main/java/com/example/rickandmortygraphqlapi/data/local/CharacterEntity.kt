package com.example.rickandmortygraphqlapi.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class CharacterEntity(
    @PrimaryKey()
    val id: String = "",
    val name: String? = "",
    val image: String? = "",
    val status: String? = "",
)
