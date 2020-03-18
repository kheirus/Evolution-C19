package com.kouelaa.coronavirus.presentation.dashboard

import com.github.mikephil.charting.formatter.ValueFormatter
import com.kouelaa.coronavirus.domain.entities.CountryValue
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

// TODO-(17/03/20)-kheirus: faire une seule fonction de formatter qui prend une liste de string date
class LineChartLabelFormatter(private val datas: List<GlobalData>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return datas[value.toInt()].Date.toChartLabelDate()
    }
}

class LineChartCountryLabelFormatter(private val datas: List<CountryValue>) : ValueFormatter() {
    override fun getFormattedValue(value: Float): String {
        return if (value < datas.size) {
            datas[value.toInt()].date.toChartLabelDate()
        } else {
            datas[datas.size-1].date.toChartLabelDate()
        }
    }
}

fun String.toChartLabelDate(): String{
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    val formatter = SimpleDateFormat("dd MMM yy")
    return formatter.format(parser.parse(this))

}