package com.example.tiendaonline.Models

data class Product(
    val Name: String,
    val Price: Float,
    val categories: List<Category>,
    val color: Any,
    val created_at: String,
    val description: String,
    val id: Int,
    val img: Img,
    val published_at: String,
    val updated_at: String
)