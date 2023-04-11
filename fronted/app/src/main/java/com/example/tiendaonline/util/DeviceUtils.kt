package com.example.tiendaonline.util

import android.content.Context
import kotlin.math.sqrt

object DeviceUtils {
    var isTablet: Boolean? = null

    fun isTablet(context: Context): Boolean {
        if (isTablet == null) {
            val displayMetrics = context.resources.displayMetrics
            val widthPx = displayMetrics.widthPixels
            val heightPx = displayMetrics.heightPixels
            val density = displayMetrics.densityDpi

            val widthDp = widthPx / density
            val heightDp = heightPx / density
            val screenInches = sqrt((widthDp * widthDp + heightDp * heightDp).toDouble()) / displayMetrics.density

            isTablet = screenInches >= 7
        }
        return isTablet!!
    }
}
