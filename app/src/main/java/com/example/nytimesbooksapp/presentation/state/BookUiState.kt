package com.example.nytimesbooksapp.presentation.state

import com.example.nytimesbooksapp.domain.model.Bookmodel

data class BookUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val books: List<Bookmodel> = emptyList(),
    val selectedBook: Bookmodel? = null,
    val error: String? = null,
    val isDarkMode: Boolean = false,
    val lastSync: String = "",
    val selectedDate: String = ""
)