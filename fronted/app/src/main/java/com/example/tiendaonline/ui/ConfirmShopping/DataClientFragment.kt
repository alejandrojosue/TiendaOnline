package com.example.tiendaonline.ui.ConfirmShopping

import android.os.Bundle
import android.os.Handler
import android.preference.PreferenceManager
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.tiendaonline.Adapter.ViewPagesAdapter
import com.example.tiendaonline.Middlewares.Validation
import com.example.tiendaonline.Middlewares.Validation.Companion.isValidEmail
import com.example.tiendaonline.Middlewares.Validation.Companion.validateTextViewRequired
import com.example.tiendaonline.R
import com.example.tiendaonline.util.Enviroments
import kotlinx.android.synthetic.main.fragment_data_client.view.*

@Suppress("DEPRECATION")
class DataClientFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_data_client, container, false)
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        view.edtDataClientName.setText(preferences.getString("fullName",""))
        view.edtDataClientEmail.setText(preferences.getString("email",""))
        val viewPage = activity?.findViewById<ViewPager2>(R.id.viewPager2)
        view.next.setOnClickListener{
            if(validateTextViewRequired(view.edtDataClientName) { true } and validateTextViewRequired(view.edtDataClientEmail
                ) { text -> isValidEmail(text) }
            ){
                Enviroments.myFullNameOrder = view.edtDataClientName.text.toString()
                Enviroments.myEmailOrder = view.edtDataClientEmail.text.toString()
                viewPage?.currentItem = 1
            }
        }
        return view
    }
}