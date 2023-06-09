package com.example.rickandmortygraphqlapi.domain

data class CharactersResults(val info: Info,val results:List<SimpleCharacter?>)

data class Info(val pages:Int?=0,val count:Int?=0,val next:Int?=0)