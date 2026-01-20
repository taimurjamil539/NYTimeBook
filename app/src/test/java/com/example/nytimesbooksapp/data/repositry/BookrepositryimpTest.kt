package com.example.nytimesbooksapp.data.repositry

import com.example.nytimesbooksapp.core.common.mapper.toEntity
import com.example.nytimesbooksapp.data.local.BookDao
import com.example.nytimesbooksapp.data.model.Book
import com.example.nytimesbooksapp.data.model.Lists
import com.example.nytimesbooksapp.data.model.Results
import com.example.nytimesbooksapp.data.model.Nytimes
import com.example.nytimesbooksapp.data.network.ApiService
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.threeten.bp.LocalDate
import org.junit.Assert.*

class BookrepositryimpTest {
    private lateinit var mockApi: ApiService
    private lateinit var mockDao: BookDao
    private lateinit var repository: BookRepositryImp
    @Before
    fun setUp() {
        mockApi= mockk()
        mockDao=mockk(relaxed = true)
        repository= BookRepositryImp(mockApi,mockDao)
    }

    @Test
    fun `getBook returns data from API when successfull`()= runTest {
        val book1 = Book(
            title = "The Power of Mind",
            book_image = "https://example.com/image1.jpg",
            description = "A deep dive into psychology",
            rank = 1,
            publisher = "Penguin",
            author = "Tamoor",
            buy_links = emptyList(),
            created_date = "2025-10-20"
        )

        val apiResponse = Nytimes(
            results = Results(
                lists = listOf(
                    Lists(books = listOf(book1))
                )
            )
        )
        coEvery { mockApi.getbooklist()} returns apiResponse
        coEvery { mockDao.clearbooks() } just runs
        coEvery { mockDao.insertbook(any()) } just runs
        coEvery { mockDao.getallbooks() } returns emptyList()

        val result=repository.getbooks()

        coVerify { mockDao.clearbooks() }
        coVerify { mockDao.insertbook(any()) }

        assertEquals(1,result.size)
        assertEquals("The Power of Mind",result[0].title)
    }

    @Test
    fun `getBooks returns cached data when API fails`()=runTest {
        coEvery { mockApi.getbooklist() } throws Exception("Network Error")
        val cachedBook = Book(
            title = "Deep work",
            book_image = "https://example.com/image1.jpg",
            description = "Focus and productivity guide.",
            rank = 1,
            publisher = "Penguin",
            author = "Tamoor",
            buy_links = emptyList(),
            created_date = "2025-10-20"

        )
        coEvery { mockDao.getallbooks() }returns listOf(cachedBook.toEntity())
        val result=repository.getbooks()
        coVerify (exactly = 0){mockDao.clearbooks()}
        coVerify(exactly = 0) { mockDao.insertbook(any()) }

        assertEquals(1,result.size)
        assertEquals("Deep work",result[0].title)



    }

    @Test
    fun `searchBooks filters data correctly`()=runTest {
        val book2=Book(
            title = "Strong Mind",
            book_image = "https://example.com/image1.jpg",
            description = "Mind is in our control.",
            rank = 1,
            publisher = "Penguin",
            author = "Tamoor",
            buy_links = emptyList(),
            created_date = "2025-10-20"

        )
        val apiResponse = Nytimes(
            results = Results(
                lists = listOf(
                    Lists(books = listOf(book2))
                )
            )
        )
        coEvery { mockApi.getbooklist() } returns apiResponse
        coEvery { mockDao.insertbook(any()) } just runs
        val result=repository.searchbooks("Strong")
        assertEquals(1,result.size)
        assertEquals("Strong Mind",result[0].title)
        coVerify { mockDao.insertbook(any()) }
    }

    @Test
    fun `getBookbyDate returns filtered data`()=runTest {
        val book3=Book(
            title = "Avengers",
            book_image = "https://example.com/image1.jpg",
            description = "The is popular movie",
            rank = 1,
            publisher = "Penguin",
            author = "Tamoor",
            buy_links = emptyList(),
            created_date = "2025-10-20"

        )
        val apiResponse = Nytimes(
            results = Results(
                lists = listOf(
                    Lists(books = listOf(book3))
                )
            )
        )
        coEvery { mockApi.getbooklist() } returns apiResponse
        coEvery { mockDao.insertbook(any()) } just runs

        val result=repository.getbookbydate(LocalDate.parse("2025-10-20"))
        assertTrue(result.any { it.title=="Avengers" })
        coVerify { mockDao.insertbook(any()) }


    }


}
