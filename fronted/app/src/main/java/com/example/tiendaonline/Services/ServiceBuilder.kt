package com.example.tiendaonline.Services

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {
    var token:String = ""
    private val authInterceptor = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {
            if(token.contains(".")){
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${token!!}")
                    .build()
                return chain.proceed(request)
            }
            return null!!
        }
    }

    // Android no maneja loalchost, usar esta ip es lo que se indica en la documentación oficial
    private const val PORT = "1337"
    const val LOCAL = "http://10.0.2.2:$PORT"
    const val WEB = "https://tiendamuebles.onrender.com"
    const val LOCALHOST = LOCAL


    private lateinit var instance: ServiceBuilder
    fun<T> buildService(service: Class<T>): T{
        lateinit var client:OkHttpClient
        if(token.contains(".")) {
            client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build()
        }else{
            client = OkHttpClient.Builder().build()
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("$LOCALHOST") // change this IP for testing by your actual machine IP
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        return retrofit.create(service)
    }
}