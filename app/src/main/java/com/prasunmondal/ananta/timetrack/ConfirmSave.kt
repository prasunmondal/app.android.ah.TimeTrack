package com.prasunmondal.ananta.timetrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.Utility.PostToSheet.SelectCustomer
import com.prasunmondal.ananta.timetrack.Utility.PostToSheet.ToSheets
import com.prasunmondal.ananta.timetrack.utils.TimeUtils
import com.prasunmondal.ananta.timetrack.values.SessionData.Singleton.instance as session

import kotlinx.android.synthetic.main.activity_confirm_save.*

class ConfirmSave : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_save)
        setSupportActionBar(toolbar)

        val confirmTimeView = findViewById<TextView>(R.id.confirmTimeView)
        val showString = TimeUtils.msToString(session.currentCustomer.getTimeDiff())
        confirmTimeView.text = showString
        println(showString)
    }

    fun onClickSave(view: View) {
        writeData()
        Toast.makeText(this,"Data Saved!", Toast.LENGTH_LONG).show()
        val i = Intent(this@ConfirmSave, SelectCustomer::class.java)
        startActivity(i)
        finish()
    }

    private fun writeData() {
        if(session.currentCustomer.inputTypeIsManual)
            ToSheets.addTransaction(session.currentCustomer, "MANUAL", applicationContext)
        else
            ToSheets.addTransaction(session.currentCustomer, "CALCULATED", applicationContext)
    }
}
