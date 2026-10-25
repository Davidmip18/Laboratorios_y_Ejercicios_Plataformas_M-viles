package com.example.laboratorio7_moviles

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*

class LocationsViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).locationDao()

    val state: StateFlow<ScreenState<List<Location>>> = dao.getAllLocations()
        .map { entities ->
            ScreenState(isLoading = false, data = entities.map { it.toModel() })
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ScreenState(isLoading = true))

    fun forceError() { }
    fun loadData() { }
}
