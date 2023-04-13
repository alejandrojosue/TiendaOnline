package com.example.tiendaonline.Filter

import com.example.tiendaonline.Models.Subcategories.SubcategoriesClient


class SubcategoryFilter {
    fun filterSubcategory(categoryName:String, subcategories: List<SubcategoriesClient>): List<SubcategoriesClient>{
        return subcategories.filter {
            it.category == categoryName
        }
    }
}