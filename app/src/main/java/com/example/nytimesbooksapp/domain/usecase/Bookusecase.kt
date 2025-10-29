package com.example.nytimesbooksapp.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.nytimesbooksapp.data.common.Resources
import com.example.nytimesbooksapp.domain.model.Bookmodel
import com.example.nytimesbooksapp.domain.reposotry.Bookrepositry
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.time.LocalDate
import javax.inject.Inject

class Bookusecase @Inject constructor (private val bookrepositry: Bookrepositry) {
    operator fun invoke(): Flow<Resources<List<Bookmodel>>> = flow {
            emit(Resources.Loading())
            val data = bookrepositry.getbooks()
            emit(Resources.Success(data))
        }.catch { e ->
            emit(Resources.Error(e.message.toString()))
        }
    suspend fun searchbooks(query: String): List<Bookmodel>{
        return bookrepositry.searchbooks(query)

    }
    @RequiresApi(Build.VERSION_CODES.O)
    suspend  fun dateby(date: String): List<Bookmodel>{
        val localDate = LocalDate.parse(date)
        return bookrepositry.getbookbydate(localDate)
    }
}
