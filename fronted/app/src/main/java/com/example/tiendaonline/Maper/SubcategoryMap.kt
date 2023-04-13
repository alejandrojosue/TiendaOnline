package com.example.tiendaonline.Maper

import com.example.tiendaonline.Models.Subcategories.DataSubcategories
import com.example.tiendaonline.Models.Subcategories.SubcategoriesClient

class SubcategoryMap {
    companion object{
        fun mapearSubcategoria(data: DataSubcategories): SubcategoriesClient{
            return SubcategoriesClient(
                id = data.id,
                Name = data.attributes.Name,
                Description = data.attributes.Description,
                category = data.attributes.category.data.attributes.Name
            )
        }
    }
}