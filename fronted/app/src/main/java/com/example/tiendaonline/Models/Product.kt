package com.example.tiendaonline.Models

data class Product(
    val Name: String,
    val Price: Int,
    val categories: List<Category>,
    val created_at: String,
    val description: String,
    val id: Int,
    val published_at: String,
    val updated_at: String
)