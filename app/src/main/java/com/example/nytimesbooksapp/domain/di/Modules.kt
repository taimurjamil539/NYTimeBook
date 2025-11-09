package com.example.nytimesbooksapp.domain.di

import com.example.nytimesbooksapp.domain.reposotry.BookRepositry
import com.example.nytimesbooksapp.domain.usecase.BookUsecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
@Module
@InstallIn(SingletonComponent::class)
object Moduless {
    @Provides
fun provideUsecase(bookrepositry: BookRepositry): BookUsecase {
    return BookUsecase(bookrepositry)
}
}

