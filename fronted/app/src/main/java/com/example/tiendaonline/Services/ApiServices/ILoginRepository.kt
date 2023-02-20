package com.example.tiendaonline.Services.ApiServices

import com.example.tiendaonline.EntityModels.Repository.LoginRequest
import com.example.tiendaonline.EntityModels.Repository.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ILoginRepository {
    @POST("auth/local")
    fun sendReq(@Body requestModel: LoginRequest) : Call<LoginResponse>
}