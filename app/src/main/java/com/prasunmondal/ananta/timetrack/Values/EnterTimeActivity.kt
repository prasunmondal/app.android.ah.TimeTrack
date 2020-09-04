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

import java.lang.String
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*


class EnterTimeActivity : AppCompatActivity() {
    var startHour: Int = 0
    var startMin: Int = 0
    var stopHour: Int = 0
    var stopMin: Int = 0
    var dateYear: Int = 0
    var dateMonth: Int = 0
    var dateDay: Int = 0
    var dateToday = ""
    var startDateTime = ""
    var stopDateTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_time)
        setSupportActionBar(toolbar)
        addChangeListeners()
        SessionData.Singleton.instance.currentCustomer.latestStartTime = System.currentTimeMillis()
        SessionData.Singleton.instance.currentCustomer.latestEndTime = System.currentTimeMillis()
    }

    fun onClickSave(view: View) {
//        saveData()
        goToSavePage()
    }

    fun goToSavePage() {
        val i = Intent(this@EnterTimeActivity, ConfirmSave::class.java)
        startActivity(i)
    }

    fun saveData() {

        SessionData.Singleton.instance.currentCustomer.latestStartTime = System.currentTimeMillis()
        val startTime = String
            .format(
                Locale.getDefault(),
                "%d:%02d:%02d", startHour,
                startMin, 0
            )

        val stopTime = String
            .format(
                Locale.getDefault(),
                "%d:%02d:%02d", stopHour,
                stopMin, 0
            )

        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        dateFormat.timeZone = TimeZone.getTimeZone("IST")
        val format = "yyyy-MM-dd "
        val sdf = SimpleDateFormat(format)

        dateToday = sdf.format(Date())

        startDateTime = dateToday + startTime + ":000"
        stopDateTime = dateToday + stopTime + ":000"

        SessionData.Singleton.instance.currentCustomer.startTime = startDateTime
        SessionData.Singleton.instance.currentCustomer.endTime = stopDateTime
    }

    private fun addChangeListeners() {
        var startTimePicker = findViewById<TimePicker>(R.id.enterData_startTimePicker)
        startTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            startHour = hourOfDay
            startMin = minute
            val t = TimeUtils.hrMinToMs(startTimePicker.hour, startTimePicker.minute)
            println(t)
            println(System.currentTimeMillis())
            SessionData.Singleton.instance.currentCustomer.latestStartTime = t

        }

        var stopTimePicker = findViewById<TimePicker>(R.id.enterData_stopTimePicker)
        stopTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            stopHour = hourOfDay
            stopMin = minute
            val t = TimeUtils.hrMinToMs(stopTimePicker.hour, stopTimePicker.minute)
            println(t)
            println(System.currentTimeMillis())
            SessionData.Singleton.instance.currentCustomer.latestEndTime = TimeUtils.hrMinToMs(stopTimePicker.hour, stopTimePicker.minute)
        }
    }
}
