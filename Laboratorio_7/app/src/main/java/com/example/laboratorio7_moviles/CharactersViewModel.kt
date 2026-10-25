package com.example.laboratorio7_moviles

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharactersViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).characterDao()

    val state: StateFlow<ScreenState<List<Character>>> = dao.getAllCharacters()
        .map { entities ->
            ScreenState(isLoading = false, data = entities.map { it.toModel() })
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), ScreenState(isLoading = true))

    fun toggleFavorite(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.updateFavoriteStatus(id, isFavorite)
        }
    }

    // Dejamos forceError por compatibilidad si la UI lo necesita
    fun forceError() { }
    fun loadData() { }
}
