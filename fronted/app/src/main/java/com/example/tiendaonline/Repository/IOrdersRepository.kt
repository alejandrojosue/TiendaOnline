package com.example.tiendaonline.Repository

import com.example.tiendaonline.Models.Orders.Order

interface IOrdersRepository {
    suspend fun createOrder(order:Order)
}