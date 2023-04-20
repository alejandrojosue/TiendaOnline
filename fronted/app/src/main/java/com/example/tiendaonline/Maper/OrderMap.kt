package com.example.tiendaonline.Maper

import com.example.tiendaonline.Models.Orders.OrderDetail
import com.example.tiendaonline.Models.Orders.OrderProduct
import com.example.tiendaonline.Models.ProductInformation.ProductsClient

class OrderMap {
    companion object{
        fun mapearOrden(productsClient: ProductsClient): OrderDetail {
            return OrderDetail(
                Quantity = productsClient.Quantity!!,
                product = OrderProduct(id = productsClient.id!!)
            )
        }
    }
}