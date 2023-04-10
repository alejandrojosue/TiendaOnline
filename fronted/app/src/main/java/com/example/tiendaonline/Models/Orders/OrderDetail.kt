package com.example.tiendaonline.Models.Orders

data class OrderDetail(
    var Quantity: Int,
    val product: OrderProduct
)