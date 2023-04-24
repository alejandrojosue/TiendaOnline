package com.example.tiendaonline.ui.ConfirmShopping

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.ActionBar
import com.example.tiendaonline.R

class Confirm_ShoppingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_shopping)
        supportActionBar?.hide()
    }

    override fun onPause() {
        super.onPause()
    }
}