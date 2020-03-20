package com.kouelaa.evolutionc19.presentation.about

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.kouelaa.evolutionc19.R
import kotlinx.android.synthetic.main.activity_about.*


class AboutActivity : AppCompatActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)

        supportActionBar?.title = getString(R.string.about_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val name  = try {
            val appInfo = packageManager.getApplicationInfo(applicationInfo.packageName, 0)
            packageManager.getApplicationLabel(appInfo)
        } catch (e: PackageManager.NameNotFoundException) {
            "-"
        }

        val version  = try {
            packageManager.getPackageInfo(packageName, 0).versionName
        } catch (e: PackageManager.NameNotFoundException) {
            "0.0"
        }

        about_version.text = "$name v$version"

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
