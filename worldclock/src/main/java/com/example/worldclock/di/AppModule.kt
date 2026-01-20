package com.example.worldclock.di

import com.example.worldclockapp.data.api.WorldTimeApi
import com.example.worldclockapp.data.repository.WorldTimeRepositoryImpl
import com.example.worldclockapp.domain.repository.WorldTimeRepository
import com.example.worldclockapp.domain.usecase.GetTimeZonesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides @Singleton
    fun provideWorldTimeApi(@Named("worldclock")retrofit: Retrofit): WorldTimeApi =
        retrofit.create(WorldTimeApi::class.java)
    @Provides @Singleton
    fun provideRepository(api: WorldTimeApi): WorldTimeRepository =
        WorldTimeRepositoryImpl(api)
    @Provides @Singleton
    fun provideGetTimeZonesUseCase(repo: WorldTimeRepository): GetTimeZonesUseCase =
        GetTimeZonesUseCase(repo)
}