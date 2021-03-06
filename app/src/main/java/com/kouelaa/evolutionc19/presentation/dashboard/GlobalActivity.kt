package com.kouelaa.evolutionc19.presentation.dashboard


import android.animation.AnimatorInflater
import android.animation.AnimatorSet
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.kouelaa.evolutionc19.R
import com.kouelaa.evolutionc19.common.toDialog
import com.kouelaa.evolutionc19.common.toErrorDialog
import com.kouelaa.evolutionc19.common.toLargeNumberFormatter
import com.kouelaa.evolutionc19.domain.entities.*
import com.kouelaa.evolutionc19.presentation.about.AboutActivity
import com.kouelaa.evolutionc19.presentation.models.ExtraDataCountry
import kotlinx.android.synthetic.main.activity_global.*
import kotlinx.android.synthetic.main.country_linechart_item.*
import kotlinx.android.synthetic.main.global_linechart_item.*
import kotlinx.android.synthetic.main.global_piechart_item.*
import kotlinx.android.synthetic.main.search_item.view.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class GlobalActivity : AppCompatActivity(){

    private val globalViewModel: GlobalViewModel by viewModel()

    private lateinit var countryAdapter: CountryAdapter
    private lateinit var countryLayoutManager: LinearLayoutManager

    private lateinit var outAnimator: AnimatorSet
    private lateinit var inAnimator: AnimatorSet
    private var isChartBackVisible = false
    private lateinit var searchDialog: AlertDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_global)

        initGlobalPieChart()
        initGlobalLineChart()
        initCountryLineChart()
        initToolbar()
        initSearchButton()

        loadAnimations()
        changeCameraDistance()
        showLoading()
    }

    override fun onStart() {
        super.onStart()

        globalViewModel.global.observe(this, Observer {global ->
            hideLoading()
            if (global != null) {
                setPieChartData(global.toGlobalChart())
                setPieChartLabels(global)
                setGlobalLineChartData(global.globalData)
                setCountriesData(global.coutriesData)

                // Display first country data
                globalViewModel.onClickFirstCountry()
            } else {
                toErrorDialog()
            }
        })

        globalViewModel.countryChartData.observe(this, Observer { countryChartValue ->
            country_item_country_tv.text = countryChartValue.country
            setCountriesLineChartDate(countryChartValue.values)
        })

        globalViewModel.searchCountry.observe(this, Observer {countrySearched ->
            if (countrySearched.found){
                countryAdapter.selected = countrySearched.index
                countryLayoutManager.scrollToPosition(countrySearched.index)
            }else{
                dialogErrorCountryNotFound()
            }
        })

        globalViewModel.selectHighlightValues.observe(this, Observer {
            globalViewModel.calculateValuesForHighlight(it)
        })

        globalViewModel.countryExtraValues.observe(this, Observer { extra ->
            initExtraCountryValues(extra)
        })

        globalViewModel.dialogUpdate.observe(this, Observer {dialogModel ->
            toDialog(dialogModel, isNegativeVisible = true){
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(dialogModel.button.url))
                if (intent.resolveActivity(packageManager) != null) {
                    startActivity(intent)
                }
            }
        })

        globalViewModel.dialogInfo.observe(this, Observer { event ->
            event.getContentIfNotHandled()?.let {dialogModel ->
                toDialog(dialogModel){
                    globalViewModel.onClickPositiveButtonDialogInfo()
                }
            }
        })
    }

    private fun dialogErrorCountryNotFound() {
        Toast.makeText(this, getString(R.string.toast_error_country_not_found), Toast.LENGTH_SHORT)
            .show()
    }

    private fun showLoading() {
        window.setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        loader.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        window.clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE)
        loader.visibility = View.GONE
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

    private fun initToolbar() {
        about_toolbar.setOnClickListener {
            startActivity(Intent(this, AboutActivity::class.java))
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }
    }

    private fun initGlobalPieChart() {
        global_piechart.setParams()
        container_global_piechart.setOnClickListener{
            animateCard()
        }
    }

    private fun initGlobalLineChart(){
        global_linechart.setParams()
    }

    private fun initCountryLineChart(){
        country_linechart.setParams()
    }

    @SuppressLint("InflateParams")
    private fun initSearchButton() {
        search_btn.setOnClickListener {
            val dialogBuilder = AlertDialog.Builder(this)
            val dialog = layoutInflater.inflate(R.layout.search_item, null)
            dialogBuilder.setView(dialog)

            searchDialog = dialogBuilder.show()

            dialog.search_edit_text.setOnEditorActionListener { textView, actionId, _ ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    globalViewModel.onSearchCountry(textView.text.toString())
                }
                searchDialog.dismiss()
                false
            }
        }
    }

    private fun initExtraCountryValues(extra: ExtraDataCountry) {
        country_item_date_tv.text = extra.countryValue.date.toExtraChartLabelDate()

        country_item_confirmed_tv.text = extra.countryValue.confirmed.toLargeNumberFormatter()
        country_item_new_confirmed_tv.text = getString(R.string._plus, extra.newConfirmed.toLargeNumberFormatter())

        country_item_death_tv.text = extra.countryValue.death.toLargeNumberFormatter()
        country_item_new_death_tv.text = getString(R.string._plus, extra.newDeath.toLargeNumberFormatter())

        country_item_recovered_tv.text = extra.countryValue.recovered.toLargeNumberFormatter()
        country_item_new_recovered_tv.text = getString(R.string._plus, extra.newRecovered.toLargeNumberFormatter())
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
            valueTextColor = ContextCompat.getColor(this@GlobalActivity, R.color.colorBackgroundLight)
            colors = sliceColors
        }
        val pieData = PieData(dataSet)
        global_piechart.data = pieData

        global_piechart.animateXY(1000, 1000)
    }

    private fun setPieChartLabels(global: Global) {
        global_piechart.centerText = getString(R.string.confirmed, global.globalData[0].confirmed.toLargeNumberFormatter())

        global.toGlobalCards().forEach {
            when(it.label){
                GlobalTypeEnum.CONFIRMED -> Unit
                GlobalTypeEnum.RECOVERED -> recovered_tv.text = getString(R.string.recovered, it.value.toLargeNumberFormatter())
                GlobalTypeEnum.DEATHS -> death_tv.text = getString(R.string.deaths, it.value.toLargeNumberFormatter())
                GlobalTypeEnum.STILL_SICK -> still_sick_tv.text = getString(R.string.still_sick, it.value.toLargeNumberFormatter())
            }
        }
    }

    private fun setGlobalLineChartData(values: List<GlobalData>){
        val lastValue = values[0]
        val valuesChart = values.reversed()

        global_item_date_tv.text = lastValue.date.toExtraChartLabelDate()
        global_item_confirmed_tv.text = lastValue.confirmed.toLargeNumberFormatter()
        global_item_death_tv.text = lastValue.deaths.toLargeNumberFormatter()
        global_item_recovered_tv.text = lastValue.recovered.toLargeNumberFormatter()

        val entriesConfirmed = ArrayList<Entry>()
        val entriesRecovered = ArrayList<Entry>()
        val entriesDeaths = ArrayList<Entry>()

        valuesChart.forEachIndexed {index, element ->
            entriesConfirmed.add(Entry(index.toFloat(), element.confirmed.toFloat()))
            entriesRecovered.add(Entry(index.toFloat(), element.recovered.toFloat()))
            entriesDeaths.add(Entry(index.toFloat(), element.deaths.toFloat()))
        }

        val lineDataSetConfirmed = LineDataSet(entriesConfirmed, "")
        val lineDataSetRecovered = LineDataSet(entriesRecovered, "")
        val lineDataSetDeaths = LineDataSet(entriesDeaths, "")

        lineDataSetConfirmed.setParams(this, R.color.colorConfirmed)
        lineDataSetRecovered.setParams(this, R.color.colorRecovered)
        lineDataSetDeaths.setParams(this, R.color.colorDeath)

        val lineData = LineData()
        lineData.addDataSet(lineDataSetConfirmed)
        lineData.addDataSet(lineDataSetRecovered)
        lineData.addDataSet(lineDataSetDeaths)
        global_linechart.xAxis.valueFormatter = LineChartLabelFormatter(valuesChart)
        global_linechart.data = lineData
        global_linechart.animateXY(1000, 1000)
    }

    private fun setCountriesLineChartDate(values: List<CountryValue>) {

        val lastValue = values[values.size-1]
        globalViewModel.calculateValuesForHighlight(lastValue)

        val entriesConfirmed = ArrayList<Entry>()
        val entriesRecovered = ArrayList<Entry>()
        val entriesDeaths = ArrayList<Entry>()

        values.forEachIndexed { index, element ->
            entriesConfirmed.add(Entry(index.toFloat(), element.confirmed.toFloat(), element))
            entriesRecovered.add(Entry(index.toFloat(), element.recovered.toFloat(), element))
            entriesDeaths.add(Entry(index.toFloat(), element.death.toFloat(), element))
        }

        val lineDataSetConfirmed = LineDataSet(entriesConfirmed, "")
        val lineDataSetRecovered = LineDataSet(entriesRecovered, "")
        val lineDataSetDeaths = LineDataSet(entriesDeaths, "")

        lineDataSetConfirmed.setParams(this, R.color.colorConfirmed)
        lineDataSetRecovered.setParams(this, R.color.colorRecovered)
        lineDataSetDeaths.setParams(this, R.color.colorDeath)

        val lineData = LineData(lineDataSetConfirmed, lineDataSetRecovered, lineDataSetDeaths)

        country_linechart.xAxis.valueFormatter = LineChartCountryLabelFormatter(values)
        country_linechart.data = lineData

        country_linechart.highlightValue((values.size - 1).toFloat(), 0, true)

        year_before.setOnClickListener {
            val xHighlighted = country_linechart.highlighted[0].x
            if (xHighlighted > 0f){
                country_linechart.highlightValue(xHighlighted - 1, 0, true)
            }
        }

        year_next.setOnClickListener {
            val xHighlighted = country_linechart.highlighted[0].x
            if (xHighlighted < values.size - 1 ){
                country_linechart.highlightValue(xHighlighted + 1, 0, true)
            }
        }

        country_linechart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onNothingSelected() {}
            override fun onValueSelected(entry: Entry?, highlight: Highlight?) {
                val data  = entry?.data as CountryValue
                globalViewModel.onChangeSelectHighlight(data)
            }
        })

        country_linechart.animateXY(1000, 200)
    }

    private fun setCountriesData(countries: List<CountryData>) {
        val countriesForAdapter = globalViewModel.getCoutriesForAdapter(countries)
        countryAdapter = CountryAdapter(this, countriesForAdapter) {globalViewModel.onClickedCountry(it)}
        countryLayoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        countries_rv.layoutManager = countryLayoutManager
        countries_rv.adapter = countryAdapter
    }
}
