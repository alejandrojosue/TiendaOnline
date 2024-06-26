package com.example.tiendaonline.Middlewares

import android.util.Patterns
import android.widget.EditText
import androidx.core.text.isDigitsOnly

class Validation {
    companion object{
        fun isValidEmail(email: String): Boolean {
            val pattern = Patterns.EMAIL_ADDRESS
            return pattern.matcher(email).matches()
        }
        fun isStrongPassword(password: String): Boolean {
            val uppercaseRegex = ".*[A-Z]+.*"
            val lowercaseRegex = ".*[a-z]+.*"
            val digitRegex = ".*\\d+.*"
            val specialCharRegex = ".*[@#\$%^&+=]+.*"

            return password.matches(uppercaseRegex.toRegex()) &&
                    password.matches(lowercaseRegex.toRegex()) &&
                    password.matches(digitRegex.toRegex()) &&
                    password.matches(specialCharRegex.toRegex())
        }
        fun validateTextViewRequired(editText: EditText, validationFunction: (String) -> Boolean): Boolean {
            val text = editText.text.toString().trim()
            return if (text.isEmpty()) {
                editText.error = "Este campo es requerido"
                editText.selectAll()
                false
            } else if (!validationFunction(text)) {
                editText.error = "El valor ingresado no es válido"
                editText.selectAll()
                false
            } else {
                editText.error = null
                true
            }
        }
        fun validateMinLength(editText: EditText, len: Int): Boolean{
            val text = editText.text.toString().trim()
            return if(text.length<len){
                editText.error = "Su identidad no es válida"
                false
            }else text.isDigitsOnly()
            editText.error = null
        }
    }
}

