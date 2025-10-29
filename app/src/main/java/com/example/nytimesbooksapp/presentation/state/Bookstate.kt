package com.example.nytimesbooksapp.presentation.state

import com.example.nytimesbooksapp.domain.model.Bookmodel

sealed class Bookstate {
   object Loading: Bookstate()
    data class Success(val books: List<Bookmodel> =emptyList()): Bookstate()
    data class Error(val message: String? = null): Bookstate()
    object Empty : Bookstate()

}