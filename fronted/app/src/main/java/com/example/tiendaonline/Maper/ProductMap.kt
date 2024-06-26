package com.example.tiendaonline.Maper

import com.example.tiendaonline.Models.ProductInformation.DataProduct
import com.example.tiendaonline.Models.ProductInformation.DataProductsQuantity
import com.example.tiendaonline.Models.V3.Product
import com.example.tiendaonline.Models.ProductInformation.Products
import com.example.tiendaonline.Models.V3.ProductClient
import com.example.tiendaonline.Models.ProductInformation.ProductsClient

class ProductMap {
    companion object {
        fun mapearV3(data: Product): ProductClient {
            return ProductClient(
                Name = data.Name,
                Price = data.Price,
                categories = data.categories,
                color = data.color,
                description = data.description,
                img = data.img
            )
        }

        fun mapearV4(data: DataProduct?): ProductsClient {
            return ProductsClient(
                id = data?.id,
                Name = data?.attributes?.Name,
                Price = data?.attributes?.Price,
                subcategory = data?.attributes?.subcategory,
                color = data?.attributes?.color,
                description = data?.attributes?.Description,
                img = data?.attributes?.Img,
                SKU = data?.attributes?.SKU,
                Quantity = data?.attributes?.Quantity,
            )
        }
        fun mapearQuantity(data: DataProductsQuantity): ProductsClient {
            return ProductsClient(
                id = data?.id,
                Name = data?.attributes?.Name,
                Price = data?.attributes?.Price,
                subcategory = null,
                color = null,
                description = data?.attributes?.Description,
                img = null,
                SKU = data?.attributes?.SKU,
                Quantity = data?.attributes?.Quantity,
            )
        }
    }
}