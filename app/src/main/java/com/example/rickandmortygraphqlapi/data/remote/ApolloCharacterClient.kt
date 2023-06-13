package com.example.rickandmortygraphqlapi.data.remote

import com.apollographql.apollo3.ApolloClient
import com.example.CharactersQuery
import com.example.rickandmortygraphqlapi.domain.CharacterClient
import com.example.rickandmortygraphqlapi.domain.CharactersResults
import com.example.rickandmortygraphqlapi.domain.DetailsCharacter
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.example.CharacterDetailsQuery
import com.example.rickandmortygraphqlapi.data.toCharacterResult
import com.example.rickandmortygraphqlapi.data.toDetailsCharacter
import com.example.rickandmortygraphqlapi.domain.Info


class ApolloCharacterClient(private val apolloClient: ApolloClient) : CharacterClient {
    override suspend fun getCharacters(page:Int): CharactersResults? {
        val response:CharactersResults?
        try {
           response=apolloClient.query(CharactersQuery(page = page))
               .execute().data?.characters?.toCharacterResult()

       }catch (exception: ApolloException){
           throw exception
       }

        return response

    }

    override suspend fun getDetailsCharacter(id: String): DetailsCharacter? {
        val response:DetailsCharacter?
        try {
            response=apolloClient.query(CharacterDetailsQuery(id = id))
                .execute().data?.character?.toDetailsCharacter()

        }catch (exception: ApolloException){
            throw exception
        }
        return response
    }
}