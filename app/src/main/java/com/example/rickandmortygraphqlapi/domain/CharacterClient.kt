package com.example.rickandmortygraphqlapi.domain

import com.apollographql.apollo3.api.Optional

interface CharacterClient {

    suspend fun getCharacters(page:Optional<Int?>):CharactersResults
    suspend fun getDetailsCharacter(id:String):DetailsCharacter


}