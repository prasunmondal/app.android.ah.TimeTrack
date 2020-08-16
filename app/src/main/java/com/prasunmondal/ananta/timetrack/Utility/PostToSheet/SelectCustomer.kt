package com.prasunmondal.ananta.timetrack.Utility.PostToSheet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.ChooseInputMethod
import com.prasunmondal.ananta.timetrack.R
import com.prasunmondal.ananta.timetrack.Values.Customer
import com.prasunmondal.ananta.timetrack.Values.SessionData.Singleton.instance as sessionData

import kotlinx.android.synthetic.main.activity_select_customer.*

class SelectCustomer : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_customer)
        setSupportActionBar(toolbar)
    }

    fun onClickInfoSaveButton(view: View) {
        println("clicked - goToCountDown")
        saveFormData()
        goToChooseInputMethod()
    }

    private fun saveFormData() {
        val name = findViewById<EditText>(R.id.selectCustomerName).text.toString()
        val phNo = findViewById<EditText>(R.id.selectCustomerPhNo).text.toString()
        val address = findViewById<EditText>(R.id.selectCustomerAddress).text.toString()
        val price = findViewById<EditText>(R.id.selectCustomerPrice).text.toString()
        sessionData.currentCustomer = Customer(name, phNo, address)
        sessionData.currentCustomer.pricePerUnit = price.toFloat()
    }

    private fun goToChooseInputMethod() {
        val i = Intent(this@SelectCustomer, ChooseInputMethod::class.java)
        startActivity(i)
        finish()
    }
}
