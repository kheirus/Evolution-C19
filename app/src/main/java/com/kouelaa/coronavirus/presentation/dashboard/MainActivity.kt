package com.kouelaa.coronavirus.presentation.dashboard

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.kouelaa.coronavirus.R
import com.kouelaa.coronavirus.domain.entities.Global
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

    private fun setPieChartCenterText(infection: Double) {
        global_chart.centerText = "${infection.toInt()} Infections"
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
        val data = PieData(dataSet).apply {
            setValueFormatter(PercentFormatter())
            setValueTextSize(10f)
            setValueTextColor(R.color.colorDeath)
        }

        global_chart.data = data
        global_chart.setDrawEntryLabels(false)

        global_chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener{
            override fun onNothingSelected() {
                // TODO-(11/03/20)-kheirus: ras
            }
            override fun onValueSelected(e: Entry?, h: Highlight?) {
                // TODO-(11/03/20)-kheirus: finir le clic sur un fromage
            }
        })

        global_chart.animateXY(1000, 1000)
    }

    private fun setCards(values: List<GlobalChartValue>) {
        values.forEach {
            when(it.label){
                GlobalTypeEnum.CONFIRMED -> {
                    confirmed_tv.apply {
                        text = it.value.toString()
                        setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorConfirmed))
                    }
                }
                GlobalTypeEnum.RECOVERED -> {
                    recovered_tv.apply {
                        text = it.value.toString()
                        setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorRecovered))
                    }
                }
                GlobalTypeEnum.DEATHS -> {
                    death_tv.apply {
                        text = it.value.toString()
                        setTextColor(ContextCompat.getColor(this@MainActivity, R.color.colorDeath))
                    }
                }
                GlobalTypeEnum.STILL_SICK -> TODO()
            }
        }
    }

    private fun initChart() {
        global_chart.apply {
            isRotationEnabled = false
            legend.isEnabled = false
            description.isEnabled = false
            setTouchEnabled(false)
            setUsePercentValues(true)


        }
    }
}
