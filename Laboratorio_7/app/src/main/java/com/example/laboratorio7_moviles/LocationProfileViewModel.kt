package com.example.laboratorio7_moviles

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationProfileViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Obtiene el locationId directo de la ruta de navegación Type-Safe
    private val locationId: Int = checkNotNull(savedStateHandle["locationId"])

    private val _state = MutableStateFlow(ScreenState<Location>())
    val state: StateFlow<ScreenState<Location>> = _state.asStateFlow()

    init { loadData() }

    fun loadData() {
        viewModelScope.launch {
            _state.value = ScreenState(isLoading = true, hasError = false)
            delay(2000)
            if (_state.value.isLoading) {
                val location = LocationDb().getLocationById(locationId)
                _state.value = ScreenState(isLoading = false, data = location)
            }
        }
    }

    fun forceError() {
        _state.value = _state.value.copy(isLoading = false, hasError = true)
    }
}
