package com.example.tiendaonline.Models.ProductInformation

data class ProductsClient(
    val id: Int?,
    val SKU: String?,
    val Name: String?,
    val Price: Double?,
    var Quantity: Int?,
    val subcategory: Subcategory?,
    val color: Color?,
    val description: String?,
    val img: ImgProduct?
)
