package com.example.tiendaonline.Services.ApiServices

import com.example.tiendaonline.Models.ProductInformation.Products
import retrofit2.Call
import retrofit2.http.GET

interface IProducts {
    @GET("/api/products?populate=*")
     fun getAll(): Call<Products>
}