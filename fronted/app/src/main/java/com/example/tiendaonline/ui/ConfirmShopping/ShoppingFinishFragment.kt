package com.example.tiendaonline.ui.ConfirmShopping

import android.content.Intent
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.text.style.UnderlineSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.example.tiendaonline.Models.Orders.Order
import com.example.tiendaonline.Models.Orders.OrderData
import com.example.tiendaonline.R
import com.example.tiendaonline.Repository.OrdersRepository
import com.example.tiendaonline.ui.ContainerActivity
import com.example.tiendaonline.util.Enviroments
import kotlinx.android.synthetic.main.fragment_shopping_finish.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ShoppingFinishFragment : Fragment() {
    private  val orderRepository = OrdersRepository()
    private val enviroments = Enviroments()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_shopping_finish, container, false)
        val viewPage = activity?.findViewById<ViewPager2>(R.id.viewPager2)
        view.finishShppoing.setOnClickListener{
            if(Enviroments.myFullNameOrder != "" && Enviroments.myEmailOrder != ""){
                crearOrden()
                startActivity(Intent(context, ContainerActivity::class.java))
                activity?.finish()
            }else{
                viewPage?.currentItem = 0
            }
        }
        view.backFinishShopping.setOnClickListener{
            viewPage?.currentItem = 0
        }

        val message = "Si desea finalizar su compra, por favor presione finalizar pero si lo que desea es agregar al pedido, presione aquí"
        val spannableString = SpannableString(message)

        val clickableSpan = object : ClickableSpan() {
            override fun onClick(widget: View) {
                Enviroments.myFullNameOrder = ""
                Enviroments.myEmailOrder = ""
                activity?.finish()
            }
        }
        spannableString.setSpan(clickableSpan, message.indexOf("aquí"), message.indexOf("aquí") + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(UnderlineSpan(), message.indexOf("aquí"), message.indexOf("aquí") + 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        view.messageTextView.text = spannableString
        view.messageTextView.movementMethod = LinkMovementMethod.getInstance()
        return view
    }
    private fun crearOrden(){
        GlobalScope.launch {
            if(Enviroments.myListOrder.size>0){
                orderRepository.createOrder(
                    Order(
                        OrderData(
                            Enviroments.myFullNameOrder,
                            Enviroments.amount,
                            Enviroments.myListOrder.toList()
                        )
                    )
                )
                enviroments.updateProductsOnClose(-1)
                Enviroments.myListOrder.clear()
                Enviroments.myListProduct.clear()
                Enviroments.amount = 0.0
                Enviroments.myFullNameOrder = ""
                Enviroments.myEmailOrder = ""
            }
        }
    }
}