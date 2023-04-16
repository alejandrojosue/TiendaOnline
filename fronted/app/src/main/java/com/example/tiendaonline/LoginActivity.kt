package com.example.tiendaonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiendaonline.Middlewares.Validation.Companion.isValidEmail
import com.example.tiendaonline.Middlewares.Validation.Companion.validateTextViewRequired
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        setUp()
    }

    private fun setUp(){
        btn_login.setOnClickListener{
            val isValid = validateTextViewRequired(edt_email) { text -> isValidEmail(text) }
        }
        btn_back.setOnClickListener {
            finish()
        }
        txt_register.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            finish()
        }
    }

    private fun btnLoginListener() {

    }

    private fun btnBackLoginListener() {

    }

    private fun txtRegisterListener() {

    }
}