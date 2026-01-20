package com.example.worldclock.di
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Named
import javax.inject.Singleton
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val BASE_URL="https://worldtimeapi.org/"
    @Provides
    @Singleton
    fun provideLogging(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC }
    @Provides
    @Singleton
    @Named("worldclock_client")
    fun provideOkHttp(logging: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder().addInterceptor(logging).build()
    @Provides
    @Singleton
    fun provideMoshi(): Moshi = Moshi.Builder().build()
    @Provides
    @Singleton
    @Named("worldclock")
    fun provideRetrofit( @Named("worldclock_client")client: OkHttpClient, moshi: Moshi): Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
}