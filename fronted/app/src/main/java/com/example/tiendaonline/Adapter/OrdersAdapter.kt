package com.example.tiendaonline.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tiendaonline.Models.Orders.OrderDetail
import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.R

class OrdersAdapter(
    private var productList:List<ProductsClient>,
    private val onClickListener:(OrderDetail) -> Unit,
    private val onClickDelete:(Int) -> Unit,
    private val orderDetailList: List<OrderDetail>
): RecyclerView.Adapter<OrdersViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return OrdersViewHolder(layoutInflater.inflate(R.layout.resource_item_orders, parent, false))
    }

    override fun getItemCount(): Int = productList.size

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val item = productList[position]
        val itemOrder = orderDetailList[position]
        holder.render(item!!, onClickListener, onClickDelete, itemOrder)
    }
}