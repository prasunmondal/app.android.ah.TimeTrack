package com.prasunmondal.ananta.timetrack

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.Utility.PostToSheet.ToSheets
import com.prasunmondal.ananta.timetrack.Values.SessionData

import kotlinx.android.synthetic.main.activity_confirm_save.*

class ConfirmSave : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_save)
        setSupportActionBar(toolbar)

        val confirmTimeView = findViewById(R.id.confirmTimeView) as TextView
        confirmTimeView.text = SessionData.Singleton.instance.currentCustomer.totalTime
        println(SessionData.Singleton.instance.currentCustomer.totalTime)
    }

    fun onClickSave(view: View) {
        writeData()
    }

    private fun writeData() {
        ToSheets.addTransaction(
            SessionData.Singleton.instance.currentCustomer,
            "Entered",
            applicationContext
        )
    }
}
