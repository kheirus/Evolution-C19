package com.kouelaa.coronavirus.presentation.dashboard

import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.Chart
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
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity(){

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initChart()
    }

    override fun onStart() {
        super.onStart()

        mainViewModel.global.observe(this, Observer {
            setCards(it.toGlobalCards())
            setPieChart(it.toGlobalChart())
            setPieChartCenterText(it.GlobalData[0].Infection)
        })
    }

    private fun initChart() {
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
            // setCenterTextTypeface(Typeface.createFromFile( "font/font.ttf"));
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
        global_chart.centerText = "${infection.toInt()} ${getString(R.string.confirmed)} "
    }

    private fun setPieChart(values: List<GlobalChartValue>) {
        val xvalues = ArrayList<PieEntry>()

        values.forEach {
            xvalues.add(PieEntry(it.value))
        }
        val dataSet = PieDataSet(xvalues, null)
        val sliceColors = mutableListOf<Int>()
        sliceColors.addAll(listOf(
            ContextCompat.getColor(this, R.color.colorDeath),
            ContextCompat.getColor(this, R.color.colorRecovered),
            ContextCompat.getColor(this, R.color.colorStillSick)
        ))
        dataSet.colors = sliceColors
        global_chart.data = PieData(dataSet).apply {
            setDrawValues(true)
            setValueFormatter(PercentFormatter())
            setValueTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorBackground))
        }

        global_chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener{
            override fun onNothingSelected() {
                // TODO-(11/03/20)-kheirus: ras
            }
            override fun onValueSelected(entry: Entry?, highlight: Highlight?) {
                // TODO-(11/03/20)-kheirus: finir le clic sur un fromage

            }
        })

        global_chart.animateXY(1000, 1000)
    }

}
