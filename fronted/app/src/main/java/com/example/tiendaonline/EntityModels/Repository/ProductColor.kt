package com.example.tiendaonline.EntityModels.Repository

data class ProductColor(
    val Quantity: Int,
    val color: Color,
    val created_at: String,
    val id: Int,
    val img: Img,
    val product: ProductX,
    val published_at: String,
    val updated_at: String
)