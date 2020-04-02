package com.kouelaa.evolutionc19.common

import java.text.DecimalFormat
import java.text.Normalizer

/**
 * Created by kheirus on 23/03/2020.
 */

const val VERSION_CODE = 12

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

