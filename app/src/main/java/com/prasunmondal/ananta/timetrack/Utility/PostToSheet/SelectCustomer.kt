package com.prasunmondal.ananta.timetrack.Utility.PostToSheet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.R
import com.prasunmondal.ananta.timetrack.TimeTracker
import com.prasunmondal.ananta.timetrack.Values.Customer
import com.prasunmondal.ananta.timetrack.Values.SessionData.Singleton.instance as sessionData

import kotlinx.android.synthetic.main.activity_select_customer.*

class SelectCustomer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_customer)
        setSupportActionBar(toolbar)
    }

    fun goToCountDown(view: View) {
        println("clicked - goToCountDown")

        var name = findViewById<EditText>(R.id.selectCustomerName).text.toString()
        var phNo = findViewById<EditText>(R.id.selectCustomerPhNo).text.toString()
        var address = findViewById<EditText>(R.id.selectCustomerAddress).text.toString()
        var price = findViewById<EditText>(R.id.selectCustomerPrice).text.toString()
        sessionData.currentCustomer = Customer(name, phNo, address)
        sessionData.currentCustomer.pricePerUnit = price.toFloat()
        
        val i = Intent(this@SelectCustomer, TimeTracker::class.java)
        startActivity(i)
        finish()
    }

}
