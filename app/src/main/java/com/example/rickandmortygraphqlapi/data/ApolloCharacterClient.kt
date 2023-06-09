package com.example.rickandmortygraphqlapi.data

import com.apollographql.apollo3.ApolloClient
import com.example.CharactersQuery
import com.example.rickandmortygraphqlapi.domain.CharacterClient
import com.example.rickandmortygraphqlapi.domain.CharactersResults
import com.example.rickandmortygraphqlapi.domain.DetailsCharacter
import com.apollographql.apollo3.api.Optional
import com.example.CharacterDetailsQuery
import com.example.rickandmortygraphqlapi.domain.Info


class ApolloCharacterClient(private val apolloClient: ApolloClient) : CharacterClient {
    override suspend fun getCharacters(page: Optional<Int?>): CharactersResults {
        return apolloClient.query(CharactersQuery(page = page))
            .execute().data?.characters?.toCharacterResult() ?: CharactersResults(
            info = Info(
                0,
                0,
                0
            ), results = emptyList()
        )
    }

    override suspend fun getDetailsCharacter(id: String): DetailsCharacter {
        return apolloClient.query(CharacterDetailsQuery(id = id))
            .execute().data?.character?.toDetailsCharacter()
            ?: DetailsCharacter("", "", "", "", "", "", "", "")
    }
}