package com.kouelaa.evolutionc19.presentation.about


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kouelaa.evolutionc19.R

class AboutActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar?.title = getString(R.string.about_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

    }

    override fun onBackPressed() {
        super.onBackPressed()

        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

}
