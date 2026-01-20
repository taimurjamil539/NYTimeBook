package com.example.nytimesbooksapp.data.di

import android.app.Application
import androidx.room.Room
import com.example.nytimesbooksapp.core.common.appconfig.AppConfig
import com.example.nytimesbooksapp.data.local.BookDao
import com.example.nytimesbooksapp.data.local.Bookdatabase
import com.example.nytimesbooksapp.data.network.ApiService
import com.example.nytimesbooksapp.data.network.Interception
import com.example.nytimesbooksapp.data.repositry.BookRepositryImp
import com.example.nytimesbooksapp.domain.reposotry.BookRepositry
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object Modules {
    @Provides
    @Singleton
    @Named("NYtimeBook")
    fun provideinterception(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val certificatePinner= CertificatePinner.Builder()
            .add("api.nytimes.com",
                "sha256/lPraBjD+VHks5/sVEDOczhg00z9TGoGMAjndDyYGUNU=")
            .build()
        return OkHttpClient.Builder()
            .certificatePinner(certificatePinner)
            .addInterceptor(loggingInterceptor)
            .addInterceptor(Interception(AppConfig.Keys.API_KEY))
            .build()
    }
    @Provides
    @Singleton
    @Named("NYtimeBook")

    fun  provideretrofit(@Named("NYtimeBook") okHttpClient: OkHttpClient): Retrofit{
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(AppConfig.Network.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }
    @Provides
    @Singleton
    fun provideApiservice(@Named("NYtimeBook")retrofit: Retrofit): ApiService {
        return retrofit.create(ApiService::class.java)
    }
    @Provides
    @Singleton
fun providedatabase(app: Application): Bookdatabase{
    return Room.databaseBuilder(
        app,
        Bookdatabase::class.java,
        "book_db")
        .build()
}
    @Provides
    @Singleton
    fun provideDao(db: Bookdatabase): BookDao = db.bookdao()
    @Provides
    @Singleton
fun provideBookRepo(apiservice: ApiService,bookDao: BookDao): BookRepositry {
    return BookRepositryImp(apiservice, bookDao)
}
}