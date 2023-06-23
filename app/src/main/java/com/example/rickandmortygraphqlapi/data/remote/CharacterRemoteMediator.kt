package com.example.rickandmortygraphqlapi.data.remote


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Database
import androidx.room.withTransaction
import coil.network.HttpException
import com.example.rickandmortygraphqlapi.data.local.CharacterDatabase
import com.example.rickandmortygraphqlapi.data.local.CharacterEntity
import com.example.rickandmortygraphqlapi.data.toCharacterEntity
import com.example.rickandmortygraphqlapi.domain.CharacterClient
import com.example.rickandmortygraphqlapi.domain.CharactersResults
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class CharacterRemoteMediator(
    private val characterDatabase: CharacterDatabase,
    private val characterClient: CharacterClient
) : RemoteMediator<Int, CharacterEntity>() {

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, CharacterEntity>
    ): MediatorResult {

        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    }else{
                        (lastItem.id!!.toInt() / state.config.pageSize ) + 1
                    }

                }
            }
            var characters:CharactersResults?=null;
                characters=characterClient.getCharacters(loadKey)
                characterDatabase.withTransaction {
                    if (loadType==LoadType.REFRESH){
                        characterDatabase.characterDao.clearAll()
                    }
                    val characterEntitys=characters?.results?.map { it!!.toCharacterEntity()}
                    if (characterEntitys != null) {
                        characterDatabase.characterDao.upsertAll(characterEntitys)
                    }
                }


            MediatorResult.Success(endOfPaginationReached = characters?.results?.isEmpty() ?: false)


        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }catch (e:Exception){
            MediatorResult.Error(e)
        }

    }
}