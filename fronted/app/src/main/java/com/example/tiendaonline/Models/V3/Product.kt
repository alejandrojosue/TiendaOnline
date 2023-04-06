package com.example.tiendaonline.Models.V3

data class Product(
    val id: Int,
    val Name: String,
    val description: String,
    val published_at: String,
//    val created_at: String,
//    val updated_at: String,
    val Price: Float,
    val color: Color,
    val img: Img,
    val categories: List<Category>
)