package com.example.nytimesbooksapp.domain.usecase

import org.junit.Test

import com.example.nytimesbooksapp.data.common.Resources
import com.example.nytimesbooksapp.domain.model.Bookmodel
import com.example.nytimesbooksapp.domain.reposotry.Bookrepositry
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import java.time.LocalDate


class BookusecaseTest {
   private val mockRepo= mockk<Bookrepositry>()
    private val usecase= Bookusecase(mockRepo)

    @Test
    fun `invoke returns Success with full Bookmodel data`() = runTest {
        val books=listOf(
            Bookmodel(bookimage = "www.example.com/image1",
                title = "This World",
                description = "The earth in circle",
                author = "Tamoor",
                publisher = "Systems Limited",
                rank = 5,
                createdDate = "2025-10-22",
                buylink = "www.example.com/buylink"
            ),
            Bookmodel(bookimage = "www.example.com/image2", title = "IGI Project", description = "in which how can tu achieve your plan Goal", author = "Pasha", publisher = "RC Shop", rank = 4, createdDate = "2025-9-15", buylink = "www.example.com/buylink2")
        )
        coEvery { mockRepo.getbooks() } returns books
        val result=usecase().drop(1).first()
        assertEquals(Resources.Success(books),result)
    }

    @Test
    fun `invoke returns Error when repository fails`()=runTest{
        coEvery { mockRepo.getbooks() } throws Exception("Network Error")
        val result=usecase().firstOrNull {it is Resources.Error}
        assertEquals("Network Error",(result as Resources.Error).message)
    }

    @Test
    fun `searchbooks returns correct filtered list`()=runTest {
        val searchResult=listOf(Bookmodel(
            bookimage = "https://example.com/bookimage3",
            title = "Deep Work",
            description = "Focus and productivity guide.",
            author = "Ahmed",
            rank = 3,
            publisher = "Grand Central",
            createdDate = "2024-12-01",
            buylink = "www.example.com/buylink3"

        ))
        coEvery { mockRepo.searchbooks("deep") }returns searchResult
        val result=usecase.searchbooks("deep")
        assertEquals(searchResult,result)
    }

    @Test
    fun `dateby returns correct list when valid date passed`()=runTest {
        val date="2024-05-15"
        val localDate= LocalDate.parse(date)
        val expectedBooks= listOf(
            Bookmodel(
                bookimage = "https://example.com/bookimage3",
                title = "Smart Work",
                description = "Focus and productivity with your mind.",
                author = "Asad",
                rank = 3,
                publisher = "Grand Central",
                createdDate = "2024-05-15",
                buylink = "www.example.com/buylink4"
            )
        )
        coEvery { mockRepo.getbookbydate((localDate)) }returns expectedBooks
        val result=usecase.dateby(date)
        assertEquals(expectedBooks,result)
    }


}