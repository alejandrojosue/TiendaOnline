package com.example.tiendaonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        btnBackRegisterListener()
        txtLoginListener()
        btnRegisterListener()
    }

    private fun btnRegisterListener() {
        if(edt_email.text.trim().isEmpty()){
            edt_email.setError("Este campo es obligatorio")
        }
        if(edt_fullname.text.trim().isEmpty()){
            edt_fullname.setError("Este campo es obligatorio")
        }
        if(edt_confirmPassword.text.trim().isEmpty()){
            edt_confirmPassword.setError("Este campo es obligatorio")
        }
        if(password.text.trim().isEmpty()) {
            password.setError("Este campo es obligatorio")
        }
    }

    private fun btnBackRegisterListener() {
        btn_back.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }

    private fun txtLoginListener() {
        txt_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }
    }
}