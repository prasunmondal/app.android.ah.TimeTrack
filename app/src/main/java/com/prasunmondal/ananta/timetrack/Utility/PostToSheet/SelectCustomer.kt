package com.prasunmondal.ananta.timetrack.Utility.PostToSheet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Spinner
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.ChooseInputMethod
import com.prasunmondal.ananta.timetrack.R
import kotlinx.android.synthetic.main.activity_select_customer.*


class SelectCustomer : AppCompatActivity() {

    private lateinit var dropdown: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_customer)
        setSupportActionBar(toolbar)

        dropdown = findViewById(R.id.custNameSelection)

        dropdown.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                pos: Int,
                id: Long
            ) {
                updateDisplayValues()
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    fun onClickInfoSaveButton(view: View) {
        println("clicked - goToCountDown")
        saveFormData()
        goToChooseInputMethod()
    }

    private fun updateDisplayValues() {
        val nameInput = dropdown.selectedItem.toString()
        val nameView = findViewById<TextView>(R.id.custNameView)
        val contactView = findViewById<TextView>(R.id.custContactView)
        val addressView = findViewById<TextView>(R.id.custAddressView)

        if(isCustomerSelectionValid()) {
            nameView.text = nameInput
            contactView.text = nameInput
            addressView.text = nameInput
        }
    }

    private fun saveFormData() {

    }

    private fun goToChooseInputMethod() {
        val i = Intent(this@SelectCustomer, ChooseInputMethod::class.java)
        startActivity(i)
        finish()
    }

    private fun isCustomerSelectionValid(): Boolean {
        return true
    }
}
