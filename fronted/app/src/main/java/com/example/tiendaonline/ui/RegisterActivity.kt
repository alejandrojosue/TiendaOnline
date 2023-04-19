package com.example.tiendaonline.ui

import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.tiendaonline.Middlewares.Validation
import com.example.tiendaonline.R
import kotlinx.android.synthetic.main.activity_register.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()
        setUp()

    }
    private fun setUp(){
        btn_back.setOnClickListener {
            finish()
        }
        txt_login.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
        btn_register.setOnClickListener{btnRegisterListener()}
    }
    private fun btnRegisterListener() {
        if(!Validation.validateTextViewRequired(edt_password) { text ->
                Validation.isStrongPassword(
                    text
                )
            }){
            Toast.makeText(
                this,
                "Contraseña insegura, usar mayúsculas, minúsculas, números y caracteres especiales",
                Toast.LENGTH_LONG
            ).show()
        }else if(!edt_confirmPassword.text.toString().trim().equals(edt_password.text.toString().trim())){
            Toast.makeText(this, "La contraseña no se confirmó correctamente", Toast.LENGTH_LONG).show()
        }else if(!Validation.validateTextViewRequired(edt_email) { text ->
                Validation.isValidEmail(
                    text
                )
            }){
            Toast.makeText(this, "Correo inválido", Toast.LENGTH_LONG).show()
        }else{
            if(Validation.validateTextViewRequired(edt_fullname) { true } &&
                Validation.validateTextViewRequired(edt_confirmPassword) { true }){
                // Registrar
                val preferences = PreferenceManager.getDefaultSharedPreferences(this)
                preferences.edit().putString("email", edt_email.text.trim().toString()).apply()
                preferences.edit().putString("fullName", edt_fullname.text.trim().toString()).apply()
                preferences.edit().putString("pass", edt_password.text.trim().toString()).apply()
                Toast.makeText(this, "Registrado correctamente", Toast.LENGTH_LONG).show()
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        }

    }
}