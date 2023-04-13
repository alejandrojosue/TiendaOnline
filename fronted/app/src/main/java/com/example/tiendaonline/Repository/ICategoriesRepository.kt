package com.example.tiendaonline.Repository

import com.example.tiendaonline.Models.Categories.Categories
import com.example.tiendaonline.Models.Categories.CategoryClient

interface ICategoriesRepository {
    suspend fun getCategories(): Result<List<CategoryClient>>
    fun clearCache()
}