package com.example.laboratorio7_moviles

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class CharacterProfileViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Obtiene el characterId directo de la ruta de navegación Type-Safe
    private val characterId: Int = checkNotNull(savedStateHandle["characterId"])

    private val _state = MutableStateFlow(ScreenState<Character>())
    val state: StateFlow<ScreenState<Character>> = _state.asStateFlow()

    init { loadData() }

    fun loadData() {
        viewModelScope.launch {
            _state.value = ScreenState(isLoading = true, hasError = false)
            delay(2000)
            if (_state.value.isLoading) {
                val character = CharacterDb().getCharacterById(characterId)
                _state.value = ScreenState(isLoading = false, data = character)
            }
        }
    }

    fun forceError() {
        _state.value = _state.value.copy(isLoading = false, hasError = true)
    }
}
