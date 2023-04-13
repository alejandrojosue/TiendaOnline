package com.example.tiendaonline.Services.ApiServices

import com.example.tiendaonline.Models.Categories.Categories
import retrofit2.Call
import retrofit2.http.GET

interface ICategory {
    @GET("/api/categories")
    fun getAll(): Call<Categories>
}