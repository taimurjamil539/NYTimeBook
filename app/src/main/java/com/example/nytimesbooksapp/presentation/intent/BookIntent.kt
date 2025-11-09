package com.example.nytimesbooksapp.presentation.intent

import com.example.nytimesbooksapp.domain.model.Bookmodel

sealed class BookIntent {
    object LoadBooks : BookIntent()
    data class SearchBooks(val query: String) : BookIntent()
    data class SelectDate(val date: String) : BookIntent()
    object Refresh : BookIntent()
    data class ToggleTheme(val isDark: Boolean) : BookIntent()
    data class SetDetail(val book: Bookmodel?) : BookIntent()
}