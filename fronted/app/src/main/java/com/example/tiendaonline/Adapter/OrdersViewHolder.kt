package com.example.tiendaonline.Adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tiendaonline.Models.Orders.OrderDetail
import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.Services.ServiceBuilder
import com.example.tiendaonline.databinding.ResourceItemOrdersBinding

class OrdersViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ResourceItemOrdersBinding.bind(view)
    fun render(
        productsClient: ProductsClient,
        onClickListener: (OrderDetail) -> Unit,
        onClickDelete: (Int, Int) -> Unit,
        orderDetail: OrderDetail,
        onClickMinusPlus: (Int, Boolean, Int) -> Unit
    ) {
        binding.tvName.setText(productsClient.Name)
        binding.tvPrice.setText("L. ${productsClient.Price.toString()}")
        var url = "${ServiceBuilder.LOCALHOST}${productsClient.img?.data?.attributes?.url}"
        Glide.with(binding.img).load(url).into(binding.img)
        binding.tvSKU.setText(" ${productsClient.SKU}")
        binding.edtQuantity.setText(" ${orderDetail.Quantity.toString()}")
        binding.delete.setOnClickListener{
            onClickDelete(adapterPosition, binding.edtQuantity.text.toString().trim().toInt())
        }
        binding.btnIncrement.setOnClickListener{
            val max: Int = productsClient.Quantity!!
            var miEntero = binding.edtQuantity.text.toString().trim().toInt()
            if(miEntero<max){
                miEntero++
                binding.edtQuantity.setText(miEntero.toString())
                onClickMinusPlus(adapterPosition, true, binding.edtQuantity.text.toString().trim().toInt())
            }
        }
        binding.btnDecrement.setOnClickListener{
            var miEntero = binding.edtQuantity.text.toString().trim().toInt()
            if(miEntero>0){
                miEntero--
                if(miEntero==0){
                    onClickDelete(adapterPosition, binding.edtQuantity.text.toString().trim().toInt())
                }else{
                    binding.edtQuantity.setText(miEntero.toString())
                    onClickMinusPlus(adapterPosition, false, binding.edtQuantity.text.toString().trim().toInt())
                }
            }
        }
    }
}