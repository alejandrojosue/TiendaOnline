package com.example.tiendaonline.Models.ProductInformation

data class AttributesProducts(
    val SKU: String?,
    val Description: String?,
    val Img: ImgProduct?,
    val Name: String?,
    val Price: Double?,
    val color: Color?,
    val Quantity: Int?,
//    val createdAt: String?,
//    val publishedAt: String?,
    val subcategory: Subcategory?,
//    val updatedAt: String?
)