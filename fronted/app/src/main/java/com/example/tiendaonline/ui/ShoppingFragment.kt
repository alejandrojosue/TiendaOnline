package com.example.tiendaonline.ui

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tiendaonline.Adapter.OrdersAdapter
import com.example.tiendaonline.Models.Orders.Order
import com.example.tiendaonline.Models.Orders.OrderData
import com.example.tiendaonline.Models.Orders.OrderDetail
import com.example.tiendaonline.Repository.OrdersRepository
import com.example.tiendaonline.Repository.ProductsRepository
import com.example.tiendaonline.databinding.FragmentShoppingBinding
import com.example.tiendaonline.ui.ConfirmShopping.Confirm_ShoppingActivity
import com.example.tiendaonline.util.Enviroments
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingFragment : Fragment() {
    private lateinit var adapter: OrdersAdapter
    private lateinit var binding: FragmentShoppingBinding
    private var columsNumber = 1
    private var manager = GridLayoutManager(context, columsNumber)
    private val productsRepository = ProductsRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(Enviroments.myListOrder.size==0) binding.tvReport.isVisible = true
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUp()
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        binding = FragmentShoppingBinding.inflate(layoutInflater)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        binding.rvOrder.layoutManager = manager
    }

    private fun setUp(){
        binding.tvTotalaPagar.setText("L. ${Enviroments.amount.toString()}")
        binding.btnComprar.setOnClickListener {
            if(Enviroments.myListOrder.size>0) startActivity(Intent(context, Confirm_ShoppingActivity::class.java)) else Toast.makeText(context, "No ha comprado nada", Toast.LENGTH_LONG).show()
        }
        initRecycleView()
    }

    private fun initRecycleView(){
        if(Enviroments.myListOrder.size==0) binding.tvReport.isVisible = true
        adapter = OrdersAdapter(
            productList = Enviroments.myListProduct.toList(),
            onClickListener = { orderDetail -> onItemSelected(orderDetail) },
            onClickDelete = { position -> onDeletedItem(position) },
            orderDetailList = Enviroments.myListOrder.toList(),
            onClickMinusPlus = {position, isPlus -> onChangeQuantity(position,isPlus)}
        )
        val decoration = DividerItemDecoration(context, manager.orientation)
        binding.rvOrder.layoutManager = manager
        binding.rvOrder.adapter = adapter
        binding.rvOrder.addItemDecoration(decoration)
    }
    private fun onDeletedItem(position:Int){
        GlobalScope.launch {
            productsRepository.updateQuantity(
                Enviroments.myListProduct[position].id!!,
                Enviroments.myListProduct[position].Quantity!!
            )
            Enviroments.myListOrder.removeAt(position)
            Enviroments.myListProduct.removeAt(position)
            activity?.runOnUiThread{initRecycleView()}
        }
    }
    private fun onItemSelected(orderDetail: OrderDetail){}

    private fun onChangeQuantity(position: Int, isPlusButton: Boolean){
        GlobalScope.launch {
            var x = 1
            if(isPlusButton) {
                x = -1
            }
            productsRepository.updateQuantity(
                Enviroments.myListProduct[position].id!!,
                Enviroments.myListProduct[position].Quantity!! - Enviroments.myListOrder[position].Quantity + x
            )
            Enviroments.myListOrder[position].Quantity -= x
        }
    }

    companion object{

    }

}