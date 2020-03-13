package com.kouelaa.coronavirus.presentation.dashboard

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.kouelaa.coronavirus.R
import com.kouelaa.coronavirus.domain.entities.GlobalChartValue
import com.kouelaa.coronavirus.domain.entities.GlobalTypeEnum
import com.kouelaa.coronavirus.domain.entities.PaysData
import kotlinx.android.synthetic.main.activity_global.*
import kotlinx.android.synthetic.main.global_piechart_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class GlobalActivity : AppCompatActivity(){

    private val globalViewModel: GlobalViewModel by viewModel()

    private lateinit var countryAdapter: CountryAdapter
    private lateinit var countryLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global)

        initGlobalChart()
    }

    override fun onStart() {
        super.onStart()

        globalViewModel.global.observe(this, Observer {
            setCards(it.toGlobalCards())
            setPieChart(it.toGlobalChart())
            setPieChartCenterText(it.GlobalData[0].Infection)
            setCountries(it.PaysData)
        })
    }

    private fun setCountries(countries: List<PaysData>) {
        val countriesForAdapter = globalViewModel.getCoutriesForAdapter(countries)
        countryAdapter = CountryAdapter(this, countriesForAdapter)
        countryLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        countries_rv.layoutManager = countryLayoutManager
        countries_rv.adapter = countryAdapter
    }

    private fun initGlobalChart() {
        global_chart.apply {
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

    private fun setCards(values: List<GlobalChartValue>) {
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
        global_chart.centerText = "${infection.toInt()} \n${getString(R.string.confirmed)} "
    }

    private fun setPieChart(values: List<GlobalChartValue>) {
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
        global_chart.data = pieData

        global_chart.animateXY(2000, 2000)
    }

}
