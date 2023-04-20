package com.example.tiendaonline.ui.ConfirmShopping

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.tiendaonline.R

class SplashShoppingFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_splash_shopping, container, false)
        Handler().postDelayed({
            if (view != null && isAdded) {
                val navController = findNavController()
                navController.navigate(R.id.action_splashShoppingFragment_to_viewPagesFragment)
            }
        }, 3000)
        return view
    }

}