package com.example.tiendaonline.Repository
import com.example.tiendaonline.Models.ProductInformation.ProductsClient

interface IProductsRepository {
    suspend fun getAll(): Result<MutableList<ProductsClient>>
    fun clearCache()
}