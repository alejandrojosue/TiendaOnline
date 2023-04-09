package com.example.tiendaonline.Models.Orders

data class Data(
    val Amount: Int,
    val ClientName: String,
    val Details: List<Detail>
)