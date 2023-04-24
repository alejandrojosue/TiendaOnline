package com.example.tiendaonline.Maper

import com.example.tiendaonline.Models.Customers.CustomerClient
import com.example.tiendaonline.Models.Customers.DataCustomers

class CustomerMap {
    companion object{
        fun mapearCliente(data: DataCustomers): CustomerClient {
            return CustomerClient(
                fullName = data.attributes.fullName,
                email = data.attributes.email,
            )
        }
    }
}