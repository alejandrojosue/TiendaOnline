package com.example.tiendaonline.Maper

import com.example.tiendaonline.Models.Product
import com.example.tiendaonline.Models.ProductClient

class ProductMap {
    companion object {
        fun mapear(data: Product): ProductClient {
            return ProductClient(
                Name = data.Name,
                Price = data.Price,
                categories = data.categories,
                color = data.color,
                description = data.description,
                img = data.img
            )
        }
    }
}