package com.example.rickandmortygraphqlapi.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert


@Dao
interface CharacterDao {

    @Upsert
    suspend fun upsertAll(characters:List<CharacterEntity>)

    @Query("Select * from characterentity")
    fun pagingSource():PagingSource<Int,CharacterEntity>

    @Query("Delete from characterentity")
    suspend fun clearAll()
}