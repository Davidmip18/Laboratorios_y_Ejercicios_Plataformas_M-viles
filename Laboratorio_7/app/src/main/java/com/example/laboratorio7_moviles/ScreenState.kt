package com.example.laboratorio7_moviles

data class ScreenState<T>(
    val isLoading: Boolean = true,
    val data: T? = null,
    val hasError: Boolean = false
)
