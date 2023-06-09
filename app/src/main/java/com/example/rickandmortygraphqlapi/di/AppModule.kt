package com.example.rickandmortygraphqlapi.di

import com.apollographql.apollo3.ApolloClient
import com.example.rickandmortygraphqlapi.data.ApolloCharacterClient
import com.example.rickandmortygraphqlapi.domain.GetCharactersUseCase
import com.example.rickandmortygraphqlapi.domain.GetDetailsCharacterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
   fun provideApolloClient():ApolloClient{
       return ApolloClient.Builder()
           .serverUrl("https://rickandmortyapi.com/graphql")
           .build()
   }

    @Provides
    @Singleton
    fun provideCharacterClient(apolloClient: ApolloClient):ApolloCharacterClient{
        return ApolloCharacterClient(apolloClient)
    }

    @Provides
    @Singleton
    fun provideGetCharactersUseCase(apolloCharacterClient: ApolloCharacterClient):GetCharactersUseCase{
        return GetCharactersUseCase(apolloCharacterClient)
    }

    @Provides
    @Singleton
    fun provideGetDetailsCharacterUseCase(apolloCharacterClient: ApolloCharacterClient):GetDetailsCharacterUseCase{
        return GetDetailsCharacterUseCase(apolloCharacterClient)
    }

}