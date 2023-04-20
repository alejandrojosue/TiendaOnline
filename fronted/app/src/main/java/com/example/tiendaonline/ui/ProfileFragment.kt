package com.example.tiendaonline.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.tiendaonline.R
import kotlinx.android.synthetic.main.activity_register.*
import kotlinx.android.synthetic.main.activity_register.view.*
import kotlinx.android.synthetic.main.fragment_profile.view.*

@Suppress("DEPRECATION")
class ProfileFragment : Fragment() {
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
            preferences.edit().putString("email", view.edt_emailProfile.text.trim().toString()).apply()
            preferences.edit().putString("fullName", view.edt_fullnameProfile.text.trim().toString()).apply()
            preferences.edit().putString("pass", view.edt_passwordProfile.text.trim().toString()).apply()
        }
        return view
    }
}