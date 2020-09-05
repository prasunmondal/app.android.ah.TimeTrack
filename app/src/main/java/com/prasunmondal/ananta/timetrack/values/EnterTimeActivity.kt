package com.prasunmondal.ananta.timetrack.values

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.ConfirmSave
import com.prasunmondal.ananta.timetrack.R
import com.prasunmondal.ananta.timetrack.utils.TimeUtils
import com.prasunmondal.ananta.timetrack.values.SessionData.Singleton.instance as session
import kotlinx.android.synthetic.main.activity_enter_time.*

class EnterTimeActivity : AppCompatActivity() {

    private lateinit var startTimePicker: TimePicker
    private lateinit var stopTimePicker: TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_time)
        setSupportActionBar(toolbar)

        startTimePicker = findViewById(R.id.enterData_startTimePicker)
        stopTimePicker = findViewById(R.id.enterData_stopTimePicker)
    }

    fun onClickSave(view: View) {
        if(isValidInput()) {
            saveData()
            goToSavePage()
        } else {
            Toast.makeText(this, "End date should be greater than start date", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveData() {
        session.currentCustomer.latestStartTime =
            TimeUtils.hrMinToMs(startTimePicker.hour, startTimePicker.minute)
        session.currentCustomer.latestEndTime =
            TimeUtils.hrMinToMs(stopTimePicker.hour, stopTimePicker.minute)
    }

    private fun goToSavePage() {
        session.currentCustomer.inputTypeIsManual = true
        val i = Intent(this@EnterTimeActivity, ConfirmSave::class.java)
        startActivity(i)
    }

    private fun isValidInput(): Boolean {
        return TimeUtils.hrMinToMs(startTimePicker.hour, startTimePicker.minute) <
                TimeUtils.hrMinToMs(stopTimePicker.hour, stopTimePicker.minute)
    }
}
