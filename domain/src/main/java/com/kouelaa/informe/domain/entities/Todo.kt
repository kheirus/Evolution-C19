package com.kouelaa.informe.domain.entities

/**
 * Created by kheirus on 2020-03-09.
 */

data class Todo(
    val id: Int = 0,
    val title: String = "",
    val completed: Boolean = false
)