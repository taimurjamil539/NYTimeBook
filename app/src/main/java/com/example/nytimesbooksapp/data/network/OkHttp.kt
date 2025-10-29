package com.example.nytimesbooksapp.data.network

import okhttp3.Interceptor
import okhttp3.Response

class Interception(private val apikey:String): Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request=chain.request()
        val newurl=request.url
            .newBuilder()
            .addQueryParameter("api-key",apikey)
            .build()

        val newrequest=request
            .newBuilder()
            .url("$newurl")
            .build()
       return chain.proceed(newrequest)

    }

}