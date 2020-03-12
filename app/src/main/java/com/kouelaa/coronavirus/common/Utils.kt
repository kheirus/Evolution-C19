package com.kouelaa.coronavirus.common

import android.content.Context
import androidx.core.content.ContextCompat
import com.kouelaa.coronavirus.R

/**
 * Created by kheirus on 12/03/2020.
 */

fun Context.getColor(color: Int): Int{
    return ContextCompat.getColor(this, color)
}