package com.kouelaa.evolutionc19.presentation.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.LargeValueFormatter
import com.kouelaa.evolutionc19.R
import com.kouelaa.evolutionc19.domain.entities.CountryData
import kotlinx.android.synthetic.main.country_item.view.*


class CountryAdapter(
    private val context: Context,
    private val countries: List<CountryData>,
    private val listener: (String) -> Unit
) : RecyclerView.Adapter<CountryAdapter.CountryViewHolder>() {

    var selected = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.country_item, parent, false)

        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return countries.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        holder.bind(countries.asReversed()[position],position,  listener)
    }

    inner class CountryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(country: CountryData, position: Int, listener: (String) -> Unit) {
            with(itemView){
                country_tv.text = country.country

                if (selected == position){
                    country_cardview.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorConfirmed))
                }else{
                    country_cardview.setCardBackgroundColor(ContextCompat.getColor(context, R.color.colorBackgroundCountry))
                }
                with(country_item_barchart) {
                    setParams()

                    val confirmed = country.confirmed.toFloat()
                    val death = country.death.toFloat()
                    val recovered = country.recovered.toFloat()

                    val entries = ArrayList<BarEntry>()
                    entries.add(BarEntry(0f, confirmed))
                    entries.add(BarEntry(0f, death))
                    entries.add(BarEntry(1f, recovered))

                    val barDataSet = BarDataSet(entries, "")
                    barDataSet.setDrawValues(true)
                    barDataSet.valueFormatter = LargeValueFormatter()

                    val colors = intArrayOf(
                        ContextCompat.getColor(context, R.color.colorConfirmed),
                        ContextCompat.getColor(context, R.color.colorDeath),
                        ContextCompat.getColor(context, R.color.colorRecovered))

                    barDataSet.colors = colors.toMutableList()
                    barDataSet.setValueTextColors(colors.toMutableList())
                    barDataSet.valueTextSize = 10f

                    val barData = BarData(barDataSet)
                    barData.barWidth = 0.8f

                    data = barData

                    setOnClickListener {
                        itemView.callOnClick()
                    }
                }

                setOnClickListener {
                    listener(country.country)
                    selected = adapterPosition
                    notifyDataSetChanged()
                }
            }
        }
    }
}