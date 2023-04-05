package com.example.tiendaonline.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaonline.Middlewares.Models.ProductClient
import com.example.tiendaonline.R

class ProductsAdapter(
    private val productList:List<ProductClient>
): RecyclerView.Adapter<ProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //Crear los items a partir de una vista (layout) llamado 'resource_item_products'
        return ProductsViewHolder(layoutInflater.inflate(R.layout.resource_item_products, parent, false))
    }

    //son la cantidad de items que mostrará el RecyclerView.Adapter<T>()
    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

}