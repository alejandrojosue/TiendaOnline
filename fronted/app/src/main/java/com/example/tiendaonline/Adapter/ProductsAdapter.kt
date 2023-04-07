package com.example.tiendaonline.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.R

class ProductsAdapter(
    private var productsList:List<ProductsClient>,
    private val onClickListener:(ProductsClient) -> Unit
): RecyclerView.Adapter<ProductsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        //Crear los items a partir de una vista (layout) llamado 'resource_item_products'
        return ProductsViewHolder(layoutInflater.inflate(R.layout.resource_item_products, parent, false))
    }

    //son la cantidad de items que mostrará el RecyclerView.Adapter<T>()
    override fun getItemCount(): Int = productsList.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item = productsList?.get(position)
        holder.render(item!!, onClickListener)
    }

    fun updateList(_productsList:List<ProductsClient>){
        this.productsList = _productsList
        notifyDataSetChanged()
    }
}