package com.example.rickandmortygraphqlapi.domain
import com.apollographql.apollo3.api.Optional

class GetCharacterUseCase(
    private val characterClient: CharacterClient
) {

    suspend fun execute(page:Optional<Int>): CharactersResults {
        return characterClient.getCharacters(page)
    }

}