package com.kouelaa.coronavirus.presentation.dashboard


import android.content.Context
import android.graphics.Typeface
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.*
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import com.kouelaa.coronavirus.R
import com.kouelaa.coronavirus.domain.entities.CountryValue
import com.kouelaa.coronavirus.domain.entities.GlobalData
import java.text.SimpleDateFormat

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

fun LineDataSet.setParams(context: Context, colorSet: Int) {
    setDrawValues(false)
    axisDependency = YAxis.AxisDependency.RIGHT
    lineWidth = 2f
    color = getColor(context, colorSet)
    mode = LineDataSet.Mode.CUBIC_BEZIER
    isHighlightEnabled = true
    setDrawCircleHole(false)
    setDrawCircles(false)
    setDrawHighlightIndicators(true)
    setDrawHorizontalHighlightIndicator(false)
    setDrawVerticalHighlightIndicator(true)
}

fun LineChart.setParams(){
    setDrawBorders(false)
    setDrawGridBackground(false)
    setPinchZoom(false)
    setScaleEnabled(false)
    isClickable = false
    legend.isEnabled = true
    legend.textColor = getColor(context, R.color.colorConfirmed)
    description = null
    legend.isEnabled = false

    xAxis.apply {
        setDrawGridLinesBehindData(false)
        setDrawLabels(true)
        setDrawAxisLine(true)
        setDrawGridLines(false)
        setScaleEnabled(false)
        axisLineWidth = 2f
        position = XAxis.XAxisPosition.BOTTOM
        textSize = 7f
        textColor = getColor(context, R.color.colorConfirmed)
        setDrawAxisLine(true)
        labelRotationAngle = -45f
    }

    axisLeft.apply {
        setDrawLabels(false)
        setDrawAxisLine(false)
        setDrawGridLines(false)
    }

    axisRight.apply {
        setDrawLabels(true)
        setDrawAxisLine(true)
        setDrawGridLines(false)
        axisLineWidth = 2f
        textColor = getColor(context, R.color.colorConfirmed)
        valueFormatter = LargeValueFormatter()
    }
}

fun PieChart.setParams(){
    isRotationEnabled = false
    legend.isEnabled = false
    description.isEnabled = false
    setTouchEnabled(false)
    setUsePercentValues(true)
    isDrawHoleEnabled = true
    setHoleColor(ContextCompat.getColor(context, R.color.colorBackground))
    setDrawCenterText(true)
    setCenterTextColor(ContextCompat.getColor(context, R.color.colorConfirmed))
    setCenterTextTypeface(Typeface.SANS_SERIF)
    setCenterTextSize(10f)
}