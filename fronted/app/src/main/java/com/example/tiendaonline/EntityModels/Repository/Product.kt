package com.example.tiendaonline.EntityModels.Repository

data class Product(
    val Name: String,
    val SKU: String,
    val colors_product: Any,
    val colors_products: List<ColorsProduct>,
    val created_at: String,
    val description: String,
    val id: Int,
    val img: Any,
    val published_at: String,
    val sub_categories: List<SubCategory>,
    val updated_at: String
)