package com.kouelaa.coronavirus.presentation.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.kouelaa.coronavirus.R
import com.kouelaa.coronavirus.domain.entities.PaysData
import kotlinx.android.synthetic.main.activity_global.view.*
import kotlinx.android.synthetic.main.country_item.view.*


class CountryAdapter(
    private val context: Context,
    private val countries: List<PaysData> // TODO-(12/03/20)-kheirus: passer que les dates d'aujourd'hui
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.country_item, parent, false)

        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries.asReversed()[position])
    }

    inner class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(country: PaysData) {
            with(itemView){
                country_tv.text = country.Pays

                with(barchart) {
                    setDrawBarShadow(false)
                    setFitBars(false)
                    setDrawBorders(false)
                    setDrawGridBackground(false)
                    setDrawValueAboveBar(true)
                    setPinchZoom(false)
                    isHighlightFullBarEnabled = false
                    isHighlightPerDragEnabled = false
                    isHighlightPerTapEnabled = false
                    isDoubleTapToZoomEnabled = false
                    isClickable = false
                    description = null

                    xAxis.apply {
                        setDrawGridLinesBehindData(false)
                        setDrawLabels(false)
                        setDrawAxisLine(false)
                        setDrawGridLines(false)
                    }

                    axisLeft.apply {
                        setDrawLabels(false)
                        setDrawLabels(false);
                        setDrawAxisLine(false)
                        setDrawGridLines(false)
                    }

                    axisRight.apply {
                        setDrawLabels(false)
                        setDrawAxisLine(false)
                        setDrawGridLines(false)
                    }

                    legend.isEnabled = false

                    val confirmed = country.Infection.toFloat()
                    val death = country.Deces.toFloat()
                    val recovered = country.Guerisons.toFloat()

                    val entries = ArrayList<BarEntry>()
                    entries.add(BarEntry(0f, confirmed))
                    entries.add(BarEntry(0f, death))
                    entries.add(BarEntry(1f, recovered))

                    val barDataSet = BarDataSet(entries, "")
                    barDataSet.setDrawValues(true)

                    val colors = intArrayOf(
                        ContextCompat.getColor(itemView.context, R.color.colorConfirmed),
                        ContextCompat.getColor(itemView.context, R.color.colorDeath),
                        ContextCompat.getColor(itemView.context, R.color.colorRecovered))

                    barDataSet.colors = colors.toMutableList()
                    barDataSet.setValueTextColors(colors.toMutableList())

                    val barData = BarData(barDataSet)
                    barData.barWidth = 0.9f

                    data = barData
                    animateY(1000)
                }
            }
        }
    }
}