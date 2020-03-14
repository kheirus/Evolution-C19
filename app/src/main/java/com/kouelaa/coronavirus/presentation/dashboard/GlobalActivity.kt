package com.kouelaa.coronavirus.presentation.dashboard

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ViewPortHandler
import com.kouelaa.coronavirus.R
import com.kouelaa.coronavirus.domain.entities.GlobalChartValue
import com.kouelaa.coronavirus.domain.entities.GlobalData
import com.kouelaa.coronavirus.domain.entities.GlobalTypeEnum
import com.kouelaa.coronavirus.domain.entities.PaysData
import kotlinx.android.synthetic.main.activity_global.*
import kotlinx.android.synthetic.main.global_linechart_item.*
import kotlinx.android.synthetic.main.global_piechart_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import kotlin.math.roundToInt


class GlobalActivity : AppCompatActivity(){

    private val globalViewModel: GlobalViewModel by viewModel()

    private lateinit var countryAdapter: CountryAdapter
    private lateinit var countryLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global)

        initGlobalPieChart()
        initGlobalLineChart()
    }

    override fun onStart() {
        super.onStart()

        globalViewModel.global.observe(this, Observer {
            setCardsData(it.toGlobalCards())
            setPieChartData(it.toGlobalChart())
            setLineChartData(it.GlobalData)
            setPieChartCenterText(it.GlobalData[0].Infection)
            setCountriesData(it.PaysData)
        })
    }

    private fun setCountriesData(countries: List<PaysData>) {
        val countriesForAdapter = globalViewModel.getCoutriesForAdapter(countries)
        countryAdapter = CountryAdapter(this, countriesForAdapter)
        countryLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        countries_rv.layoutManager = countryLayoutManager
        countries_rv.adapter = countryAdapter
    }

    private fun initGlobalPieChart() {
        global_piechart.apply {
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
    }

    private fun initGlobalLineChart() {
        global_linechart.apply {
            setDrawBorders(false)
            setDrawGridBackground(false)
            setPinchZoom(true)
            isClickable = false

            xAxis.apply {
                setDrawGridLinesBehindData(false)
                setDrawLabels(false)
                setDrawAxisLine(false)
                setDrawGridLines(false)
            }
            axisLeft.apply {
                setDrawLabels(true)
                setDrawLabels(false)
                setDrawAxisLine(false)
                setDrawGridLines(false)
                //axisMaximum = 80_000f

            }
            axisRight.apply {
                setDrawLabels(false)
                setDrawAxisLine(true)
                setDrawGridLines(false)
            }

            legend.isEnabled = false

        }
    }

    private fun setCardsData(values: List<GlobalChartValue>) {
        values.forEach {
            when(it.label){
                GlobalTypeEnum.CONFIRMED -> Unit
                GlobalTypeEnum.RECOVERED -> {
                    recovered_tv.apply {
                        text = getString(R.string.recovered) + "\n"+ it.value.toInt().toString()
                    }
                }
                GlobalTypeEnum.DEATHS -> {
                    death_tv.apply {
                        text = getString(R.string.deaths)+ "\n" + it.value.toInt().toString()
                    }
                }
                GlobalTypeEnum.STILL_SICK -> {
                    still_sick_tv.apply {
                        text = getString(R.string.still_sick)+ "\n" + it.value.toInt().toString()
                    }
                }
            }
        }
    }

    private fun setPieChartCenterText(infection: Double) {
        global_piechart.centerText = "${infection.toInt()} \n${getString(R.string.confirmed)} "
    }

    private fun setPieChartData(values: List<GlobalChartValue>) {
        val xValues = ArrayList<PieEntry>()

        values.forEach {
            xValues.add(PieEntry(it.value))
        }
        val dataSet = PieDataSet(xValues, null)
        val sliceColors = mutableListOf<Int>()
        sliceColors.addAll(listOf(
            ContextCompat.getColor(this, R.color.colorDeath),
            ContextCompat.getColor(this, R.color.colorRecovered),
            ContextCompat.getColor(this, R.color.colorStillSick)
        ))
        dataSet.apply {
            setDrawValues(true)
            valueFormatter = PercentFormatter()
            valueTextSize = 8f
            valueTextColor = ContextCompat.getColor(this@GlobalActivity, R.color.colorBackgroundCountry)
            colors = sliceColors
        }
        val pieData = PieData(dataSet)
        global_piechart.data = pieData

        global_piechart.animateXY(2000, 2000)
    }

    private fun setLineChartData(values: List<GlobalData>){
        val entries = ArrayList<Entry>()
        values.reversed().forEachIndexed {index, element ->
            entries.add(Entry(index.toFloat(), element.Infection.toFloat()))
        }

        val lineDataSet = LineDataSet(entries, "")

        lineDataSet.apply {
            setDrawValues(false)
            axisDependency = YAxis.AxisDependency.RIGHT
            lineWidth = 2f
            setDrawCircleHole(false)
            setDrawCircles(false)
            color = ContextCompat.getColor(this@GlobalActivity, R.color.colorConfirmed)
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            setDrawHighlightIndicators(true)
            setDrawHorizontalHighlightIndicator(false)
            setDrawVerticalHighlightIndicator(false)
        }


        val lineData = LineData(lineDataSet)

        global_linechart.data = lineData
        global_linechart.animateXY(1000, 1000)
    }

    class MyFormatter(val symbol: String = "") : IValueFormatter {
        override fun getFormattedValue(
            value: Float,
            entry: Entry,
            dataSetIndex: Int,
            viewPortHandler: ViewPortHandler
        ): String {
            return value.roundToInt().toString() + symbol // append a unit with value
        }
    }

}
