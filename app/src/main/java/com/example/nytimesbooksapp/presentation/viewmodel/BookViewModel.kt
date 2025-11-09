package com.example.nytimesbooksapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nytimesbooksapp.data.common.Resources
import com.example.nytimesbooksapp.domain.usecase.BookUsecase
import com.example.nytimesbooksapp.presentation.intent.BookIntent
import com.example.nytimesbooksapp.presentation.state.BookUiState
import com.example.nytimesbooksapp.ui.theme.Keyprefs
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookViewModel @Inject constructor(
    private val usecase: BookUsecase,
    private val datastore: Keyprefs
) : ViewModel() {

    private val _state = MutableStateFlow(BookUiState())
    val state: StateFlow<BookUiState> = _state.asStateFlow()

    val intentChannel = Channel<BookIntent>(Channel.UNLIMITED)

    init {
        handleIntents()
    }

    private fun handleIntents() {
        viewModelScope.launch {
            intentChannel.consumeAsFlow().collect { intent ->
                when (intent) {
                    is BookIntent.LoadBooks -> loadBooks()
                    is BookIntent.SearchBooks -> searchBooks(intent.query)
                    is BookIntent.SelectDate -> loadBooksByDate(intent.date)
                    is BookIntent.Refresh -> refreshBooks()
                    is BookIntent.ToggleTheme -> toggleTheme(intent.isDark)
                    is BookIntent.SetDetail -> _state.update { it.copy(selectedBook = intent.book) }
                }
            }
        }
    }

    private fun loadBooks(forceRefresh: Boolean = false) {
        viewModelScope.launch {
            usecase().collect { result ->
                when (result) {
                    is Resources.Loading -> _state.update { it.copy(isLoading = true, error = null) }
                    is Resources.Success -> {
                        val data = result.data ?: emptyList()
                        _state.update {
                            it.copy(
                                isLoading = false,
                                books = data,
                                error = if (data.isEmpty()) "No data found" else null
                            )
                        }
                    }
                    is Resources.Error -> _state.update {
                        it.copy(isLoading = false, error = result.message ?: "Unknown error")
                    }
                }
            }
        }
    }

    private fun searchBooks(query: String) {
        viewModelScope.launch {
            if (query.isBlank()) {
                loadBooks()
                return@launch
            }
            try {
                _state.update { it.copy(isLoading = true) }
                val result = usecase.searchbooks(query)
                _state.update { it.copy(isLoading = false, books = result, error = null) }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun loadBooksByDate(date: String) {
        viewModelScope.launch {
            try {
                _state.update { it.copy(isLoading = true, selectedDate = date) }
                val result = usecase.dateby(date)
                _state.update {
                    it.copy(
                        isLoading = false,
                        books = result,
                        error = if (result.isEmpty()) "No books for this date" else null
                    )
                }
            } catch (e: Exception) {
                _state.update { it.copy(isLoading = false, error = e.message) }
            }
        }
    }

    private fun refreshBooks() {
        viewModelScope.launch {
            _state.update { it.copy(isRefreshing = true) }
            loadBooks(forceRefresh = true)
            val now = java.text.SimpleDateFormat("hh:mm:ss a", java.util.Locale.getDefault())
                .format(java.util.Date())
            datastore.savelastsync(now)
            _state.update { it.copy(isRefreshing = false, lastSync = now) }
        }
    }

    private fun toggleTheme(isDark: Boolean) {
        viewModelScope.launch {
            datastore.savetheme(isDark)
            _state.update { it.copy(isDarkMode = isDark) }
        }
    }
}