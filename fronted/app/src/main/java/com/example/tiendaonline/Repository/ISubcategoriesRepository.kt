package com.example.tiendaonline.Repository

import com.example.tiendaonline.Models.Subcategories.SubcategoriesClient

interface ISubcategoriesRepository {
    suspend fun get(): Result<List<SubcategoriesClient>>
    fun clearCache()
}