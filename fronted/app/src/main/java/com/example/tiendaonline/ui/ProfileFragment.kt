package com.example.tiendaonline.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiendaonline.Models.Users.RegisterRequest
import com.example.tiendaonline.Models.Users.User
import com.example.tiendaonline.R
import com.example.tiendaonline.Repository.UserRepository
import com.example.tiendaonline.util.ToastMessage
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {
    private val userRepository = UserRepository()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        view.edt_emailProfile.setText(preferences.getString("email", ""))
        view.edt_fullnameProfile.setText(preferences.getString("fullName", ""))
        view.edt_passwordProfile.setText(preferences.getString("pass", ""))
        view.btn_saveProfile.setOnClickListener{
            GlobalScope.launch {
                val user = RegisterRequest(
                    username = view.edt_fullnameProfile.text.trim().toString(),
                    Identification = preferences.getString("identidad", "")!!,
                    email = view.edt_emailProfile.text.trim().toString(),
                    password = view.edt_passwordProfile.text.trim().toString()
                )
                if(preferences.getInt("id", -1) != -1){
                    val response = userRepository.updateUser(preferences.getInt("id", -1), user)
                    if(response.isSuccess){
                        preferences.edit().putString("email", view.edt_emailProfile.text.trim().toString()).apply()
                        preferences.edit().putString("fullName", view.edt_fullnameProfile.text.trim().toString()).apply()
                        preferences.edit().putString("pass", view.edt_passwordProfile.text.trim().toString()).apply()
                        activity?.runOnUiThread {
                            ToastMessage.getToast(view.context, "Datos guardados Correctamente!")
                        }
                    }else{
                        if(response.getOrNull()!!.equals("400")){
                            activity?.runOnUiThread {
                                ToastMessage.getToast(view.context, "Usuario y/o correo ya existen")
                            }
                        }
                    }
                }
            }
        }
        return view
    }
}