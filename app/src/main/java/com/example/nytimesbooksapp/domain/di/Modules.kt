package com.example.nytimesbooksapp.domain.di

import com.example.nytimesbooksapp.domain.reposotry.Bookrepositry
import com.example.nytimesbooksapp.domain.usecase.Bookusecase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
@Module
@InstallIn(SingletonComponent::class)
object Moduless {
    @Provides
fun provideUsecase(bookrepositry: Bookrepositry): Bookusecase {
    return Bookusecase(bookrepositry)
}
}

