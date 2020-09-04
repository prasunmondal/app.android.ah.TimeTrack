package com.prasunmondal.ananta.timetrack.Values

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.ConfirmSave
import com.prasunmondal.ananta.timetrack.R
import com.prasunmondal.ananta.timetrack.Utils.TimeUtils
import kotlinx.android.synthetic.main.activity_enter_time.*

class EnterTimeActivity : AppCompatActivity() {

    lateinit var startTimePicker: TimePicker
    lateinit var stopTimePicker: TimePicker

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_time)
        setSupportActionBar(toolbar)

        startTimePicker = findViewById(R.id.enterData_startTimePicker)
        stopTimePicker = findViewById(R.id.enterData_stopTimePicker)

        SessionData.Singleton.instance.currentCustomer.latestStartTime =
            TimeUtils.hrMinToMs(startTimePicker.hour, startTimePicker.minute)
        SessionData.Singleton.instance.currentCustomer.latestEndTime =
            TimeUtils.hrMinToMs(stopTimePicker.hour, stopTimePicker.minute)

        addChangeListeners()
    }

    fun onClickSave(view: View) {
        goToSavePage()
    }

    fun goToSavePage() {
        val i = Intent(this@EnterTimeActivity, ConfirmSave::class.java)
        startActivity(i)
    }

    private fun addChangeListeners() {
        startTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            SessionData.Singleton.instance.currentCustomer.latestStartTime =
                TimeUtils.hrMinToMs(startTimePicker.hour, startTimePicker.minute)

        }

        stopTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            SessionData.Singleton.instance.currentCustomer.latestEndTime =
                TimeUtils.hrMinToMs(stopTimePicker.hour, stopTimePicker.minute)
        }
    }
}
