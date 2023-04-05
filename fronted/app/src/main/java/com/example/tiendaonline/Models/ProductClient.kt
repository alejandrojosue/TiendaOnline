package com.example.tiendaonline.Models

data class ProductClient(
    val Name: String,
    val Price: Float,
    val categories: List<Category>,
    val color: Any,
    val description: String,
    val img: Img,
)
