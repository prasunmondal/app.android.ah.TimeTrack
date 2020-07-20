package com.prasunmondal.ananta.timetrack.Utility.PostToSheet

import android.content.Intent
import android.os.Bundle
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.R
import com.prasunmondal.ananta.timetrack.TimeTracker

import kotlinx.android.synthetic.main.activity_select_customer.*

class SelectCustomer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_customer)
        setSupportActionBar(toolbar)
    }

    fun goToCountDown(view: View) {
        println("clicked - goToCountDown")
        val i = Intent(this@SelectCustomer, TimeTracker::class.java)
        startActivity(i)
        finish()
    }

}
