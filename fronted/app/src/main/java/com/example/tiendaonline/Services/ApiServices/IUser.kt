package com.example.tiendaonline.Services.ApiServices

import com.example.tiendaonline.Models.Users.*
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface IUser {
    @POST("/api/auth/local")
    fun login(@Body requestModel: LoginRequest) : Call<LoginResponse>
    @POST("/api/auth/local/register")
    fun register(@Body requestModel: RegisterRequest) : Call<RegisterResponse>
    @PUT("/api/users/{id}")
    fun updateUser(@Path("id") id: Int, @Body requestModel: RegisterRequest) : Call<User>
}