package com.example.laboratorio7_moviles

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LocationProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).locationDao()
    private val _state = MutableStateFlow(ScreenState<Location>(isLoading = true))
    val state: StateFlow<ScreenState<Location>> = _state.asStateFlow()

    fun loadData(id: Int) {
        viewModelScope.launch {
            dao.getLocationById(id).collect { entity ->
                if (entity != null) {
                    _state.value = ScreenState(isLoading = false, data = entity.toModel())
                }
            }
        }
    }

    fun forceError() {
        _state.value = _state.value.copy(isLoading = false, hasError = true)
    }
}
