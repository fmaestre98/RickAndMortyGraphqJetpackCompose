package com.example.rickandmortygraphqlapi.domain


class GetCharactersUseCase(
    private val characterClient: CharacterClient
) {

    suspend fun execute(page:Int): CharactersResults {
        return characterClient.getCharacters(page)
    }

}