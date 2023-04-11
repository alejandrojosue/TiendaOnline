package com.example.tiendaonline.Filter

import com.example.tiendaonline.Models.ProductInformation.ProductsClient

class ProductFilter {
    fun filterProduct(productFilter:String, productMutableList: MutableList<ProductsClient>):List<ProductsClient> {
        return productMutableList.filter { productsClient ->
            productsClient.Name!!.lowercase()
                .contains(productFilter.lowercase()) || productsClient.description!!.lowercase()
                .contains(productFilter.lowercase())
        }
    }
}