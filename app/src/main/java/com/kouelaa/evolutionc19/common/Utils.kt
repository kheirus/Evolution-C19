package com.kouelaa.evolutionc19.common

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.gson.Gson
import com.kouelaa.evolutionc19.R
import com.kouelaa.evolutionc19.presentation.models.ButtonModel
import com.kouelaa.evolutionc19.presentation.models.DialogModel
import java.text.DecimalFormat
import java.text.Normalizer
import java.text.NumberFormat
import java.util.*

/**
 * Created by kheirus on 23/03/2020.
 */

/**
 * Normalize string - convert to lowercase, replace diacritics and trim trailing whitespaces
 */
fun String.normalize(): String {
    return Normalizer.normalize(toLowerCase(Locale.ROOT), Normalizer.Form.NFD)
        .replace("[\\p{InCombiningDiacriticalMarks}]".toRegex(), "").trim()
}

/**
 * Print 12,3k instead of 12300
 */
fun Double.toLargeNumberFormatter() : String {
    val defaultFormat = DecimalFormat("#.#")
    return when(this){
        in 0f..999f -> defaultFormat.format(this).toString()
        in 1000f..999_999f -> defaultFormat.format(this/1000).toString() + "k"
        in 1_000_000f..999_999_999f -> {
            val formatMillion = DecimalFormat("#.##")
            formatMillion.format(this/1_000_000).toString() + "m"
        }
        else -> defaultFormat.format(this).toString()
    }
}

fun Float.toLargeNumberFormatter() : String {
    val defaultFormat = DecimalFormat("#.#")
    return when(this){
        in 0f..999f -> defaultFormat.format(this).toString()
        in 1000f..999_999f -> defaultFormat.format(this/1000).toString() + "k"
        in 1_000_000f..999_999_999f -> {
            val formatMillion = DecimalFormat("#.##")
            formatMillion.format(this/1_000_000).toString() + "m"
        }
        else -> defaultFormat.format(this).toString()
    }
}

fun FirebaseRemoteConfig.toDialogModel(key: String, gson: Gson): DialogModel?{
    val jsonRemoteDialog = getString(key)
    return gson.fromJson<DialogModel>(jsonRemoteDialog, DialogModel::class.java)
}


fun AppCompatActivity.toDialog(
    model: DialogModel,
    isNegativeVisible:Boolean = false,
    func: (() -> Unit)? = {}
): AlertDialog? {

    return AlertDialog.Builder(this).apply {
        setTitle(model.title)
        setMessage(model.content)
        if (isNegativeVisible){
            setNegativeButton(R.string._ignore) { dialog, _ ->
                dialog.dismiss()
            }
        }
        setPositiveButton(model.button.label) { dialog, _ ->
            func?.invoke()
            dialog.dismiss()
        }
    }.show()
}

fun AppCompatActivity.toErrorDialog(){
    val dialogModel = DialogModel(
        title = getString(R.string.dialog_title),
        content = getString(R.string.dialog_text),
        button = ButtonModel(
            label = getString(android.R.string.yes),
            url = null
        )
    )
    toDialog(dialogModel){ finish() }
}


