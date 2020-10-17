package com.example.demo.api

import okhttp3.Response
import okhttp3.Interceptor

class AuthorizationInterceptor(val tokenType: String, val token: String): Interceptor {
    
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val authorizedRequest = request.newBuilder()
                                    .addHeader("Authorization", tokenType + " " + token)
                                    .build()
        val response = chain.proceed(authorizedRequest)

        return response
    }
}
