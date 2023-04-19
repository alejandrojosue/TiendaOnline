package com.example.tiendaonline.Adapter
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tiendaonline.ui.ContainerActivity
import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.Services.ServiceBuilder
import com.example.tiendaonline.databinding.ResourceItemProductsBinding

class ProductsViewHolder(view: View):RecyclerView.ViewHolder(view) {
    private val binding = ResourceItemProductsBinding.bind(view)
    fun render(productsClient: ProductsClient, onClickListener:(ProductsClient) -> Unit){
        binding.tvName.setText(productsClient.Name)
        binding.tvPrice.setText("L. ${productsClient.Price.toString()}")
        binding.tvDescription.setText(productsClient.description)
        var url = "${ServiceBuilder.LOCALHOST}${productsClient.img?.data?.attributes?.url}"
        Glide.with(binding.img).load(url).into(binding.img)
        binding.tvSKU.setText(" ${productsClient.SKU}")
        binding.tvQuantity.setText(" ${productsClient.Quantity.toString()}")
        binding.tvMoreInfo.setOnClickListener{
            ContainerActivity.moreInfo(binding.tvDescription.context,
                binding.tvName.text.toString(),
                "Precio: "+binding.tvPrice.text.toString(),
                "Cantidad disponible: "+binding.tvQuantity.text.toString(),
                binding.tvDescription.text.toString())
        }
        binding.agregar.setOnClickListener{
            if((binding.tvQuantity.text.toString().trim().toInt())>0) onClickListener(productsClient) else Toast.makeText(binding.tvQuantity.context, "No hay producto suficiente", Toast.LENGTH_LONG).show()
        }
        itemView.setOnClickListener{}
    }
}