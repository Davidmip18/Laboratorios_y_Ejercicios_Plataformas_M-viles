package com.example.laboratorio7_moviles

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CharacterProfileViewModel(application: Application) : AndroidViewModel(application) {
    private val dao = AppDatabase.getDatabase(application).characterDao()
    private val _state = MutableStateFlow(ScreenState<Character>(isLoading = true))
    val state: StateFlow<ScreenState<Character>> = _state.asStateFlow()

    fun loadData(id: Int) {
        viewModelScope.launch {
            dao.getCharacterById(id).collect { entity ->
                if (entity != null) {
                    _state.value = ScreenState(isLoading = false, data = entity.toModel())
                }
            }
        }
    }

    fun toggleFavorite(id: Int, isFavorite: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dao.updateFavoriteStatus(id, isFavorite)
        }
    }

    fun forceError() { }
}
