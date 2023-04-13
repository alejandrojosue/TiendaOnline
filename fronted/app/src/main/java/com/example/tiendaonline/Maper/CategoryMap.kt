package com.example.tiendaonline.Maper

import com.example.tiendaonline.Models.Categories.CategoryClient
import com.example.tiendaonline.Models.Categories.DataCategories

class CategoryMap {
    companion object{
        fun mapearCategoria(data: DataCategories): CategoryClient{
            return CategoryClient(
                id = data.id,
                Description = data.attributes.Description,
                Name = data.attributes.Name
            )
        }
    }
}