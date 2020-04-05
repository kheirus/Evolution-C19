package com.kouelaa.evolutionc19.framework.local

import android.content.Context
import android.content.SharedPreferences

/**
 * Created by kheirus on 01/04/2020.
 */


fun initSharedPreferences(context: Context): SharedPreferences {
    return context.getSharedPreferences("evolution_c19_shared_pref", Context.MODE_PRIVATE)
}