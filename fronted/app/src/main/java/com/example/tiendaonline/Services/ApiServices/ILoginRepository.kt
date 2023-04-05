package com.example.tiendaonline.Services.ApiServices

import com.example.tiendaonline.Middlewares.Models.LoginRequest
import com.example.tiendaonline.Middlewares.Models.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ILoginRepository {
    @POST("auth/local")
    fun sendReq(@Body requestModel: LoginRequest) : Call<LoginResponse>
}