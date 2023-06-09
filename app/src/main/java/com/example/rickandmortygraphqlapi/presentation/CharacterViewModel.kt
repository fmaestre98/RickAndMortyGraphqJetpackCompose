package com.example.rickandmortygraphqlapi.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickandmortygraphqlapi.domain.DetailsCharacter
import com.example.rickandmortygraphqlapi.domain.GetCharactersUseCase
import com.example.rickandmortygraphqlapi.domain.GetDetailsCharacterUseCase
import com.example.rickandmortygraphqlapi.domain.Info
import com.example.rickandmortygraphqlapi.domain.SimpleCharacter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val getCharactersUseCase: GetCharactersUseCase,
    private val getDetailsCharacterUseCase: GetDetailsCharacterUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CharactersState())
    val state = _state.asStateFlow()

    init {
      getData()
    }

    fun getData(){
        viewModelScope.launch {
            _state.update {
                it.copy(
                    isLoading = true
                )
            }

            _state.update {
                try {
                    val charactersResults = getCharactersUseCase.execute(0)
                    it.copy(
                        info = charactersResults.info,
                        characters = charactersResults.results,
                        isLoading = false
                    )
                }catch (e:Exception){
                    it.copy(
                        isLoading = false,
                        error = true
                    )
                }


            }

        }
    }

    fun selectCharacter(id: String):Unit {
        viewModelScope.launch {
            _state.update {
                it.copy(
                    selectedCharacter = getDetailsCharacterUseCase.execute(id)
                )
            }
        }
    }

    fun removeSelectedCharacter() {
        _state.update {
            it.copy(
                selectedCharacter = null
            )
        }
    }

    data class CharactersState(
        val info: Info? = null,
        val characters: List<SimpleCharacter?> = emptyList(),
        val isLoading: Boolean = false,
        val selectedCharacter: DetailsCharacter? = null,
        val error:Boolean=false

    )

}