package com.example.nytimesbooksapp.data.repositry

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.nytimesbooksapp.data.common.mapper.toDomain
import com.example.nytimesbooksapp.data.common.mapper.toEntity
import com.example.nytimesbooksapp.data.local.BookDao
import com.example.nytimesbooksapp.data.network.ApiService
import com.example.nytimesbooksapp.domain.model.Bookmodel
import com.example.nytimesbooksapp.domain.reposotry.Bookrepositry
import java.time.LocalDate
import kotlin.collections.emptyList


class Bookrepositryimp(private val apiService: ApiService, private val bookDao: BookDao):
    Bookrepositry {
    override suspend fun getbooks(): List<Bookmodel> {

        return try {

            val reposnse=apiService.getbooklist().results.lists.flatMap { it.books }
            val entities=reposnse.map { it.toEntity()}
            bookDao.clearbooks()
            bookDao.insertbook(entities)

            entities.map { it.toDomain()}
        }
        catch (e: Exception){
            val cachedbook=bookDao.getallbooks()
            cachedbook.map { it.toDomain()}
        }
    }

    override suspend fun searchbooks(query:String): List<Bookmodel> {
        return try {
            val allbooks=apiService.getbooklist().results.lists.flatMap { it.books }
            val filterr=allbooks.filter {
                        it.title.contains(query,ignoreCase=true) ||
                        it.author.contains(query, ignoreCase = true)           }
            if (filterr.isNotEmpty()){
             val entites=filterr.map { it.toEntity() }
             bookDao.insertbook(entites)
             entites.map { it.toDomain() }

            }
            else{
                emptyList()
            }
        }

        catch (e:Exception){
            val cache=bookDao.getallbooks()
            return cache.filter {
                it.title.contains(query, ignoreCase = true)
                it.auther.contains(query, ignoreCase = true)
            }.map { it.toDomain() }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getbookbydate(publishedAfter: LocalDate): List<Bookmodel> {
        return try {
            val allbooks = apiService.getbooklist().results.lists.flatMap { it.books }

            val filtered = allbooks.filter { book ->
                val bookdate = try {
                    LocalDate.parse(book.created_date.substring(0, 10))
                } catch (e: Exception) {
                    LocalDate.now()
                }

                bookdate.isBefore(publishedAfter) || bookdate.isEqual(publishedAfter)
            }.ifEmpty {
                allbooks
            }

            if (filtered.isNotEmpty()) {
                val entities = filtered.map { it.toEntity() }
                bookDao.insertbook(entities)
                entities.map { it.toDomain() }
            } else {
                emptyList()
            }

        } catch (e: Exception) {
            val cache = bookDao.getallbooks()

            cache.filter { entity ->
                val date = try {
                    LocalDate.parse(entity.createDate.substring(0, 10))
                } catch (e: Exception) {
                    LocalDate.now()
                }
                date.isAfter(publishedAfter) || date.isEqual(publishedAfter)
            }.map { it.toDomain() }
        }
    }

}
