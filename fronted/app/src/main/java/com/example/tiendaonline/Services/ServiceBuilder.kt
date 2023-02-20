package com.example.tiendaonline.Services

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object ServiceBuilder {
    private val token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MywiaWF0IjoxNjc2NzgwNjMxLCJleHAiOjE2NzkzNzI2MzF9.MRwZ9OzgvGHxQeBFoqPcHQwCZXon1IPYSEI8_ABfaQI"
    private val authInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer ${token}")
                .build()
            return chain.proceed(request)
        }
    }
//    var client = OkHttpClient.Builder().build()
var client = OkHttpClient.Builder()
    .addInterceptor(authInterceptor)
    .build()



    // Android no maneja loalchost, usar esta ip es lo que se indica en la documentación oficial
    private const val localhost = "10.0.2.2"

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://$localhost:1337/") // change this IP for testing by your actual machine IP
        .addConverterFactory(GsonConverterFactory.create())
        .client(client)
        .build()

    fun<T> buildService(service: Class<T>): T{
        return retrofit.create(service)
    }
}