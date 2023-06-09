package com.example.rickandmortygraphqlapi.data

import com.example.CharacterDetailsQuery
import com.example.CharactersQuery
import com.example.rickandmortygraphqlapi.domain.CharactersResults
import com.example.rickandmortygraphqlapi.domain.DetailsCharacter
import com.example.rickandmortygraphqlapi.domain.Info
import com.example.rickandmortygraphqlapi.domain.SimpleCharacter

fun CharacterDetailsQuery.Character.toDetailsCharacter():DetailsCharacter{
    return DetailsCharacter(
        id=id,
        name=name,
        image=image,
        gender = gender,
        status = status,
        species = species,
        type = type,
        location = location?.name,
        origin = origin?.name
    )
}

fun CharactersQuery.Result.toSimpleCharacter():SimpleCharacter{
    return SimpleCharacter(
        id=id,
        name=name,
        status = status,
        image = image
    )
}

fun CharactersQuery.Characters.toCharacterResult():CharactersResults{
    return CharactersResults(
        info = Info(pages = info?.pages, count = info?.count, next = info?.next),
        results = results?.map { result -> result?.toSimpleCharacter() } ?: emptyList()
    )
}