package com.example.tiendaonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiendaonline.Middlewares.validation.Companion.isValidEmail
import com.example.tiendaonline.Middlewares.validation.Companion.validateTextViewRequired
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.activity_login.view.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        btnBackLoginListener()
        txtRegisterListener()
        btnLoginListener()
    }

    private fun btnLoginListener() {
        btn_login.setOnClickListener{
            val isValid = validateTextViewRequired(edt_email) { text -> isValidEmail(text) }
        }
    }

    private fun btnBackLoginListener() {
        btn_back.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun txtRegisterListener() {
        txt_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }
    }
}