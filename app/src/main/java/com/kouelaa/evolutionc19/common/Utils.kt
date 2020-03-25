package com.kouelaa.evolutionc19.common

import android.icu.text.CompactDecimalFormat
import android.icu.text.NumberFormat
import android.icu.util.ULocale
import java.text.DecimalFormat
import java.text.Normalizer
import java.util.*
import kotlin.math.abs

/**
 * Created by kheirus on 23/03/2020.
 */


/**
 * Normalize string - convert to lowercase, replace diacritics and trim trailing whitespaces
 */
fun String.normalize(): String {
    return Normalizer.normalize(toLowerCase(), Normalizer.Form.NFD)
        .replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "").trim()
}

/**
 * Print 12,3k instead of 12300
 */
fun Double.toKFormatter() : String {
    val df = DecimalFormat("#.#")
    return if (this > 999) df.format(this/1000).toString() + "k"
    else df.format(this).toString()
}

fun Float.toKFormatter() : String {
    val df = DecimalFormat("#.#")
    return if (this > 999) df.format(this/1000).toString() + "k"
    else df.format(this).toString()
}

