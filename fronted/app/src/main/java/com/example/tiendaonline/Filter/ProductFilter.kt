package com.example.tiendaonline.Filter

import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.Models.Subcategories.Subcategories
import com.example.tiendaonline.Models.Subcategories.SubcategoriesClient

class ProductFilter {
    fun filterProduct(productFilter:String, productMutableList: MutableList<ProductsClient>):List<ProductsClient> {
        return productMutableList.filter { productsClient ->
            productsClient.Name!!.lowercase()
                .contains(productFilter.lowercase()) || productsClient.description!!.lowercase()
                .contains(productFilter.lowercase())
        }
    }
    fun filterProductByCategory(productMutableList: MutableList<ProductsClient>, subcategories: List<SubcategoriesClient>):List<ProductsClient>{

        return productMutableList.filter {productClient ->
            subcategories.any{ subcategoryClient ->
                subcategoryClient.Name == productClient.subcategory!!.data!!.attributes!!.Name
            }
        }
    }
    fun filterProductBySubcategory(subcategories: String, productMutableList: MutableList<ProductsClient>):List<ProductsClient>{
        return productMutableList.filter { productsClient ->
            productsClient.subcategory?.data?.attributes?.Name == subcategories
        }
    }
}