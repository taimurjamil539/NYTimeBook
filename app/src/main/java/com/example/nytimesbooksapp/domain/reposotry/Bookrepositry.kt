package com.example.nytimesbooksapp.domain.reposotry

import com.example.nytimesbooksapp.domain.model.Bookmodel
import java.time.LocalDate

interface Bookrepositry {
    suspend fun getbooks(): List<Bookmodel>
    suspend fun searchbooks(query: String): List<Bookmodel>
    suspend fun getbookbydate(publishedAfter: LocalDate): List<Bookmodel>
}
