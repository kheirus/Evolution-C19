package com.kouelaa.evolutionc19.common

import android.app.Activity
import android.content.Context.INPUT_METHOD_SERVICE
import android.view.View
import android.view.inputmethod.InputMethodManager

/**
 * Created by kheirus on 23/03/2020.
 */


fun View.showSoftKeyboard() {
    if (requestFocus()) {
        val imm = context.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    }
}

fun View.hideKeyboard() {
    (context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(windowToken, 0)
}

fun Activity.hideKeyboard() = (if (currentFocus == null) View(this) else currentFocus)?.hideKeyboard()