package com.example.tiendaonline.Adapter
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.Services.ServiceBuilder
import com.example.tiendaonline.databinding.ResourceItemProductsBinding
class ProductsViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding = ResourceItemProductsBinding.bind(view)
    fun render(productsClient: ProductsClient, onClickListener:(ProductsClient) -> Unit){
        binding.tvName.setText(productsClient.Name)
        binding.tvPrice.setText("L. ${productsClient.Price.toString()}")
        binding.tvDescription.setText(productsClient.description)
        var url = "${ServiceBuilder.localhost}${productsClient.img?.data?.attributes?.url}"
        Glide.with(binding.img).load(url).into(binding.img)
        binding.img.setOnClickListener{
            Toast.makeText(binding.img.context,binding.tvName.text,Toast.LENGTH_LONG).show()
        }
        binding.tvSKU.setText(productsClient.SKU)
        binding.tvQuantity.setText(productsClient.Quantity.toString())
        itemView.setOnClickListener{onClickListener(productsClient)}
    }
}