package com.example.tiendaonline.Services.ApiServices

import com.example.tiendaonline.Models.Subcategories.Subcategories
import retrofit2.Call
import retrofit2.http.GET

interface ISubcategory {
    @GET("/api/subcategories?populate=*")
    fun getAll(): Call<Subcategories>
}