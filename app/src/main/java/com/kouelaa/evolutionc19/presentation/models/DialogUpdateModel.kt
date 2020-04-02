package com.kouelaa.evolutionc19.presentation.models

/**
 * Created by kheirus on 23/03/2020.
 */
data class DialogUpdateModel (
    val version: Int,
    val title: String,
    val content: String,
    val button: ButtonModel
)

data class ButtonModel (
    val label: String,
    val url: String
)