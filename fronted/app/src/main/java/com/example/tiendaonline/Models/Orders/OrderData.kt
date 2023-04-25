package com.example.tiendaonline.Models.Orders

data class OrderData(
    val CustomerName: String,
    val email: String,
    val Amount: Double,
    val Details: List<OrderDetail>
)