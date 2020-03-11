package com.kouelaa.coronavirus.presentation.dashboard

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.kouelaa.coronavirus.R
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val mainViewModel: MainViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    override fun onStart() {
        super.onStart()

        mainViewModel.globalData.observe(this, Observer {
            confirmed_tv.text = it.GlobalData[0].Infection.toInt().toString()
            recovered_tv.text = it.GlobalData[0].Guerisons.toInt().toString()
            death_tv.text = it.GlobalData[0].Deces.toInt().toString()
        })
    }
}
