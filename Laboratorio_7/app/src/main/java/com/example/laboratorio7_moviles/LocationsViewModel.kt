package com.example.laboratorio7_moviles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationsViewModel : ViewModel() {
    private val _state = MutableStateFlow(ScreenState<List<Location>>())
    val state: StateFlow<ScreenState<List<Location>>> = _state.asStateFlow()

    init { loadData() }

    fun loadData() {
        viewModelScope.launch {
            _state.value = ScreenState(isLoading = true, hasError = false)
            delay(4000)
            if (_state.value.isLoading) {
                val data = LocationDb().getAllLocations()
                _state.value = ScreenState(isLoading = false, data = data)
            }
        }
    }

    fun forceError() {
        _state.value = _state.value.copy(isLoading = false, hasError = true)
    }
}
