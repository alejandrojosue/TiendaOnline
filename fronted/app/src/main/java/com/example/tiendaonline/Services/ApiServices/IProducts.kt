package com.example.tiendaonline.Services.ApiServices

import com.example.tiendaonline.Models.Orders.ProductUpdate
import com.example.tiendaonline.Models.ProductInformation.Products
import com.example.tiendaonline.Models.ProductInformation.ProductsQuantity
import com.example.tiendaonline.Models.V3.Product
import retrofit2.Call
import retrofit2.http.*

interface IProducts {
    @GET("/api/products?populate=*")
     fun getAll(): Call<Products>
    @GET("/api/products/{id}?populate=*")
    fun getById(@Path("id") id: Int): Call<ProductsQuantity>
    @GET("/api/products?populate=*&pagination[limit]=100")
    fun getLimit(@Query("pagination[limit]") id: Int): Call<Products>
    @PUT("/api/products/{id}")
    fun putQuantity(@Path("id") id: Int, @Body productUpdate: ProductUpdate): Call<Products>
}