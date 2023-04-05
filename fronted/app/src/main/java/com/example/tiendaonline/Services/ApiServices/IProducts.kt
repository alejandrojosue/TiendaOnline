package com.example.tiendaonline.Services.ApiServices

import com.example.tiendaonline.Middlewares.Models.Product
import retrofit2.Call
import retrofit2.http.GET

interface IProducts {
    @GET("products")
     fun getAll(): Call<List<Product>>
}