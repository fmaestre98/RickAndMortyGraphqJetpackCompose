package com.example.rickandmortygraphqlapi.domain

data class CharactersResults(val info: Info,val results:List<SimpleCharacter?>)

data class Info(val pages:Int?,val count:Int?,val next:Int?)