package com.example.rickandmortygraphqlapi.domain


class GetDetailsCharacterUseCase(
    private val characterClient: CharacterClient
) {

    suspend fun execute(id: String): DetailsCharacter {
        return characterClient.getDetailsCharacter(id)
    }

}