package com.example.tiendaonline.Middlewares.Models

data class Img(
    val alternativeText: String,
    val caption: String,
//    val created_at: String,
//    val ext: String,
    val formats: Formats,
//    val hash: String,
    val height: Int,
    val id: Int,
    val mime: String,
    val name: String,
    val previewUrl: Any,
    val provider: String,
//    val provider_metadata: Any,
    val size: Double,
//    val updated_at: String,
    val url: String,
    val width: Int
)