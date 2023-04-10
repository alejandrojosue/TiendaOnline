package com.example.tiendaonline.Models.Orders

data class OrderData(
    val Amount: Double,
    val ClientName: String,
    val Details: List<OrderDetail>
)