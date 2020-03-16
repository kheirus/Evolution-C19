package com.kouelaa.coronavirus.presentation.dashboard


import android.animation.Animator
import android.animation.AnimatorInflater
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.graphics.Typeface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.components.XAxis.XAxisPosition
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.kouelaa.coronavirus.R
import com.kouelaa.coronavirus.domain.entities.*
import kotlinx.android.synthetic.main.activity_global.*
import kotlinx.android.synthetic.main.country_linechart_item.*
import kotlinx.android.synthetic.main.global_linechart_item.*
import kotlinx.android.synthetic.main.global_piechart_item.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class GlobalActivity : AppCompatActivity(){

    private val globalViewModel: GlobalViewModel by viewModel()

    private lateinit var countryAdapter: CountryAdapter
    private lateinit var countryLayoutManager: LinearLayoutManager

    private lateinit var outAnimator: AnimatorSet
    private lateinit var inAnimator: AnimatorSet
    private var isChartBackVisible = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global)

        initGlobalLineChart()
        initGlobalPieChart()

        initCountryLineChart()

        loadAnimations()
        changeCameraDistance()

    }

    override fun onStart() {
        super.onStart()

        globalViewModel.global.observe(this, Observer {
            setPieChartData(it.toGlobalChart())
            setPieChartLabels(it)
            setGlobalLineChartData(it.GlobalData)
            setCountriesData(it.PaysData)
        })

        globalViewModel.country.observe(this, Observer {
            country_item_country_tv.text = it
        })
    }

    private fun changeCameraDistance() {
        val distance = 8000
        val scale = resources.displayMetrics.density * distance
        container_global_piechart.cameraDistance = scale
        container_global_linechart.cameraDistance = scale
    }

    private fun loadAnimations() {
        outAnimator = AnimatorInflater.loadAnimator(this, R.animator.out_animation) as AnimatorSet
        inAnimator = AnimatorInflater.loadAnimator(this, R.animator.in_animation) as AnimatorSet
    }

    private fun animateCard() {
        if (!inAnimator.isRunning && !outAnimator.isRunning) {
            if (!isChartBackVisible) {
                outAnimator.setTarget(container_global_piechart)
                inAnimator.setTarget(container_global_linechart)
                outAnimator.start()
                inAnimator.start()
                isChartBackVisible = true

            } else {
                outAnimator.setTarget(container_global_linechart)
                inAnimator.setTarget(container_global_piechart)
                outAnimator.start()
                inAnimator.start()
                isChartBackVisible = false
            }
        }
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

        container_global_piechart.setOnClickListener{
            animateCard()
        }
    }

    private fun initGlobalLineChart() {
        global_linechart.apply {
            setDrawBorders(false)
            setDrawGridBackground(false)
            setPinchZoom(false)
            isClickable = false

            xAxis.apply {
                setDrawGridLinesBehindData(false)
                setDrawLabels(true)
                setDrawAxisLine(true)
                setDrawGridLines(false)
                axisLineWidth = 2f
                position = XAxisPosition.BOTTOM
                textSize = 7f
                textColor = ContextCompat.getColor(context, R.color.colorConfirmed)
                setDrawAxisLine(true)
                labelRotationAngle = -45f


                // set a custom value formatter
                // set a custom value formatter
                //xAxis.valueFormatter = MyCustomFormatter()
            }
            axisLeft.apply {
                setDrawLabels(true)
                setDrawLabels(false)
                setDrawAxisLine(false)
                setDrawGridLines(false)
                //axisMaximum = 80_000f

            }
            axisRight.apply {
                setDrawLabels(true)
                setDrawAxisLine(true)
                setDrawGridLines(false)
                axisLineWidth = 2f
                textColor = ContextCompat.getColor(context, R.color.colorConfirmed)
                valueFormatter = LargeValueFormatter()

            }

            legend.isEnabled = true
            legend.textColor = ContextCompat.getColor(context, R.color.colorConfirmed)

            container_global_linechart.setOnClickListener {
                animateCard()
            }

        }
    }

    private fun initCountryLineChart() {
//        country_linechart.apply {
//            setDrawBorders(false)
//            setDrawGridBackground(false)
//            setPinchZoom(false)
//            isClickable = false
//
//            xAxis.apply {
//                setDrawGridLinesBehindData(false)
//                setDrawLabels(true)
//                setDrawAxisLine(true)
//                setDrawGridLines(false)
//                axisLineWidth = 2f
//                position = XAxisPosition.BOTTOM
//                textSize = 7f
//                textColor = ContextCompat.getColor(context, R.color.colorConfirmed)
//                setDrawAxisLine(true)
//                labelRotationAngle = -45f
//
//
//                // set a custom value formatter
//                // set a custom value formatter
//                //xAxis.valueFormatter = MyCustomFormatter()
//            }
//            axisLeft.apply {
//                setDrawLabels(true)
//                setDrawLabels(false)
//                setDrawAxisLine(false)
//                setDrawGridLines(false)
//                //axisMaximum = 80_000f
//
//            }
//            axisRight.apply {
//                setDrawLabels(true)
//                setDrawAxisLine(true)
//                setDrawGridLines(false)
//                axisLineWidth = 2f
//                textColor = ContextCompat.getColor(context, R.color.colorConfirmed)
//                valueFormatter = LargeValueFormatter()
//
//            }
//
//            legend.isEnabled = true
//            legend.textColor = ContextCompat.getColor(context, R.color.colorConfirmed)
//
//        }
    }

    private fun setCountriesData(countries: List<PaysData>) {
        val countriesForAdapter = globalViewModel.getCoutriesForAdapter(countries)
        countryAdapter = CountryAdapter(this, countriesForAdapter) {globalViewModel.onClickedCountry(it)}
        countryLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        countries_rv.layoutManager = countryLayoutManager
        countries_rv.adapter = countryAdapter
    }

    private fun setPieChartLabels(global: Global) {
        global_piechart.centerText = "${global.GlobalData[0].Infection} \n${getString(R.string.confirmed)}"

        global.toGlobalCards().forEach {
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
            valueFormatter = PieChartValueFormatter()
            valueTextSize = 8f
            valueTextColor = ContextCompat.getColor(this@GlobalActivity, R.color.colorBackgroundCountry)
            colors = sliceColors
        }
        val pieData = PieData(dataSet)
        global_piechart.data = pieData

        global_piechart.animateXY(2000, 2000)
    }

    private fun setGlobalLineChartData(values: List<GlobalData>){
        val entriesConfirmed = ArrayList<Entry>()
//        val entriesRecovered = ArrayList<Entry>()
//        val entriesDeaths = ArrayList<Entry>()
        val valuesChart = values.reversed()

        valuesChart.forEachIndexed {index, element ->
            entriesConfirmed.add(Entry(index.toFloat(), element.Infection.toFloat()))
//            entriesRecovered.add(Entry(index.toFloat(), element.Guerisons.toFloat()))
//            entriesDeaths.add(Entry(index.toFloat(), element.Deces.toFloat()))
        }

        val lineDataSetConfirmed = LineDataSet(entriesConfirmed, "")
//        var lineDataSetRecovered = LineDataSet(entriesRecovered, "")
//        var lineDataSetDeaths = LineDataSet(entriesDeaths, "")

        lineDataSetConfirmed.apply {
            setDrawValues(false)
            axisDependency = YAxis.AxisDependency.RIGHT
            lineWidth = 2f
            color = ContextCompat.getColor(this@GlobalActivity, R.color.colorConfirmed)
            mode = LineDataSet.Mode.LINEAR
            isHighlightEnabled = true
            setDrawCircleHole(false)
            setDrawCircles(false)
            setDrawHighlightIndicators(true)
            setDrawHorizontalHighlightIndicator(false)
            setDrawVerticalHighlightIndicator(true)

        }

        val lineData = LineData(lineDataSetConfirmed)
        global_linechart.xAxis.valueFormatter = LineChartLabelFormatter(valuesChart)
        global_linechart.data = lineData
        global_linechart.animateXY(1000, 1000)
    }
}
