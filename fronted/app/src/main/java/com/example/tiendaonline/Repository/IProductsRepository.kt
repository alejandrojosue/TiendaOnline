package com.example.tiendaonline.Repository
import com.example.tiendaonline.Models.ProductInformation.ProductsClient

interface IProductsRepository {
    suspend fun getAll(): Result<MutableList<ProductsClient>>
    suspend fun getById(id: Int): Result<ProductsClient>
    suspend fun updateQuantity(id: Int, quantity: Int)
    fun clearCache()
}