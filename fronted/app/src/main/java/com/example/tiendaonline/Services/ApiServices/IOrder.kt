package com.example.tiendaonline.Services.ApiServices

import com.example.tiendaonline.Models.Orders.Order
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IOrder {
    @POST("/api/orders")
    fun create(@Body order: Order): Call<Order>
}