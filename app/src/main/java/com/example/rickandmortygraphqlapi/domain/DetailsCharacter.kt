package com.example.rickandmortygraphqlapi.domain

data class DetailsCharacter(
    val id:String?="",
    val name: String? = "",
    val image: String? = "",
    val status: String? = "",
    val species: String? = "",
    val type: String? = "",
    val gender: String? = "",
    val location: String? = "",
    val origin: String? = ""
)