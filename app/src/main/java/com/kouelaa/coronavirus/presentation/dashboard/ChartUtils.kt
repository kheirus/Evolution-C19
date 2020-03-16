package com.kouelaa.coronavirus.presentation.dashboard

import com.github.mikephil.charting.formatter.ValueFormatter
import com.kouelaa.coronavirus.domain.entities.GlobalData
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter

/**
 * Created by kheirus on 15/03/2020.
 */

class PieChartValueFormatter : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return "%.1f".format(value).toString() + "%"
    }
}

class LineChartLabelFormatter(private val datas: List<GlobalData>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return datas[value.toInt()].Date.toChartLabelDate()
    }
}

fun String.toChartLabelDate(): String{
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val formatter = SimpleDateFormat("dd MMM yy")
    return formatter.format(parser.parse(this))

}