package com.example.rickandmortygraphqlapi.domain



interface CharacterClient {

    suspend fun getCharacters(page:Int):CharactersResults?
    suspend fun getDetailsCharacter(id:String):DetailsCharacter?


}