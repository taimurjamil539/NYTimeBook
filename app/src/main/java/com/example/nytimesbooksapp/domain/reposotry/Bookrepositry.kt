package com.example.nytimesbooksapp.domain.reposotry

import com.example.nytimesbooksapp.domain.model.Bookmodel
import org.threeten.bp.LocalDate


interface Bookrepositry {
    suspend fun getbooks(): List<Bookmodel>
    suspend fun searchbooks(query: String): List<Bookmodel>
    suspend fun getbookbydate(publishedAfter: LocalDate): List<Bookmodel>
}
