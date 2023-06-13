package com.example.rickandmortygraphqlapi.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import androidx.room.RoomDatabase
import com.apollographql.apollo3.ApolloClient
import com.example.rickandmortygraphqlapi.data.local.CharacterDatabase
import com.example.rickandmortygraphqlapi.data.local.CharacterEntity
import com.example.rickandmortygraphqlapi.data.remote.ApolloCharacterClient
import com.example.rickandmortygraphqlapi.data.remote.CharacterRemoteMediator
import com.example.rickandmortygraphqlapi.domain.GetCharactersUseCase
import com.example.rickandmortygraphqlapi.domain.GetDetailsCharacterUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApolloClient(): ApolloClient {
        return ApolloClient.Builder()
            .serverUrl("https://rickandmortyapi.com/graphql")
            .build()
    }

    @Provides
    @Singleton
    fun provideCharacterClient(apolloClient: ApolloClient): ApolloCharacterClient {
        return ApolloCharacterClient(apolloClient)
    }

    @Provides
    @Singleton
    fun provideGetCharactersUseCase(apolloCharacterClient: ApolloCharacterClient): GetCharactersUseCase {
        return GetCharactersUseCase(apolloCharacterClient)
    }

    @Provides
    @Singleton
    fun provideGetDetailsCharacterUseCase(apolloCharacterClient: ApolloCharacterClient): GetDetailsCharacterUseCase {
        return GetDetailsCharacterUseCase(apolloCharacterClient)
    }

    @Provides
    @Singleton
    fun provideCharacterDataBase(@ApplicationContext context: Context): CharacterDatabase {
        return Room.databaseBuilder(
            context = context,
            CharacterDatabase::class.java,
            "characters.db"
        ).build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideCharacterPager(
        characterDatabase: CharacterDatabase,
        apolloCharacterClient: ApolloCharacterClient
    ): Pager<Int, CharacterEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = CharacterRemoteMediator(characterDatabase, apolloCharacterClient),
            pagingSourceFactory = {
                characterDatabase.characterDao.pagingSource()
            }
        )
    }


}