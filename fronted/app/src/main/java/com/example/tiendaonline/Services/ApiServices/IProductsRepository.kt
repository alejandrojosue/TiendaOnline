package com.example.tiendaonline.Services.ApiServices

import com.example.tiendaonline.Models.Product
import retrofit2.Call
import retrofit2.http.GET

interface IProductsRepository {
    @GET("products")
    fun getAll(): Call<List<Product>>
}