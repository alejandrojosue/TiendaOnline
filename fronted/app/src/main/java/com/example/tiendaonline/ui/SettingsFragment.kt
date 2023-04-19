package com.example.tiendaonline.ui

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.tiendaonline.R
import com.google.android.material.switchmaterial.SwitchMaterial

@Suppress("DEPRECATION")
class SettingsFragment : Fragment() {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val swt: SwitchMaterial = view.findViewById(R.id.mode)
        val preferences = PreferenceManager.getDefaultSharedPreferences(view.context)
        swt.isChecked = preferences.getBoolean("DarkMode",true)
        swt.setOnCheckedChangeListener { _, changeState ->
            if (changeState) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES) else AppCompatDelegate.setDefaultNightMode(
                AppCompatDelegate.MODE_NIGHT_NO
            )
            preferences.edit().putBoolean("DarkMode", changeState).apply()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }
}