package com.example.tiendaonline

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.tiendaonline.Middlewares.Validation.Companion.isStrongPassword
import com.example.tiendaonline.Middlewares.Validation.Companion.isValidEmail
import com.example.tiendaonline.Middlewares.Validation.Companion.validateTextViewRequired
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        setUp()
        btnRegisterListener()
    }
    private fun setUp(){
        btn_back.setOnClickListener {
            finish()
        }
        txt_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
    private fun btnRegisterListener() {
        if(validateTextViewRequired(edt_email){text-> isValidEmail(text)} and
        validateTextViewRequired(edt_fullname){true} &&
        validateTextViewRequired(edt_password){text-> isStrongPassword(text)} and
        validateTextViewRequired(edt_confirmPassword){true} &&
        edt_confirmPassword.text.trim().equals(edt_password.text.trim())){
            // Registrar
        }
    }
}