package com.example.tiendaonline.Models.ProductInformation

data class ProductsClient(
    val Name: String?,
    val Price: Double?,
    val subcategory: Subcategory?,
    val color: Color?,
    val description: String?,
    val img: ImgProduct?
)
