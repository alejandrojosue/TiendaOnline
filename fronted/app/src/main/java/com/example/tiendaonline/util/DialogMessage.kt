package com.example.tiendaonline.util

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.example.tiendaonline.Models.ProductInformation.ProductsClient
import com.example.tiendaonline.Services.ServiceBuilder
import com.example.tiendaonline.databinding.DialogMessageBinding

class DialogMessage(
    private val onSubmitClickListener: (Int)->Unit,
    private val productsClient: ProductsClient
):DialogFragment() {
    private lateinit var binding: DialogMessageBinding
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        binding = DialogMessageBinding.inflate(LayoutInflater.from(context))
        val builder = AlertDialog.Builder(requireActivity())
        builder.setView(binding.root)
        binding.tvName.setText(productsClient.Name)
        binding.tvPrice.setText("L. ${productsClient.Price.toString()}")
        var url = "${ServiceBuilder.LOCALHOST}${productsClient.img?.data?.attributes?.url}"
        Glide.with(binding.img).load(url).into(binding.img)
        binding.tvSKU.setText(" ${productsClient.SKU}")
        binding.edtQuantity.setText("1")

        binding.btnIncrement.setOnClickListener{
            val max: Int = productsClient.Quantity!!
            val miString = binding.edtQuantity.text.toString()
            var miEntero = miString.toInt()
            if(miEntero<max){
                miEntero++
                binding.edtQuantity.setText(miEntero.toString())
            }
        }
        binding.btnDecrement.setOnClickListener{
            var miEntero = binding.edtQuantity.text.toString().trim().toInt()
            if(miEntero>0){
                miEntero--
                binding.edtQuantity.setText(miEntero.toString())
            }
        }
        binding.btnAnadir.setOnClickListener{
            onSubmitClickListener(binding.edtQuantity.text.toString().trim().toInt())
            dismiss()
        }
        val dialog = builder.create()
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }
}