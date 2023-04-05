package com.example.tiendaonline.Repository
import com.example.tiendaonline.Middlewares.Models.ProductClient
interface IProductsRepository {
    suspend fun getAll(): Result<List<ProductClient>>
}