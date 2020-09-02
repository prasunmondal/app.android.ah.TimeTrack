package com.prasunmondal.ananta.timetrack.Values

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.ConfirmSave
import com.prasunmondal.ananta.timetrack.R
import com.prasunmondal.ananta.timetrack.Utility.PostToSheet.SelectCustomer
import com.prasunmondal.ananta.timetrack.Utility.PostToSheet.ToSheets
import kotlinx.android.synthetic.main.activity_enter_time.*
import java.lang.String
import java.text.SimpleDateFormat
import java.util.*

class EnterTimeActivity : AppCompatActivity() {
    var startHour:Int = 0
    var startMin:Int = 0
    var stopHour:Int = 0
    var stopMin:Int = 0
    var dateYear:Int = 0
    var dateMonth:Int = 0
    var dateDay:Int = 0
    var dateToday = ""
    var startDateTime = ""
    var stopDateTime = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_time)
        setSupportActionBar(toolbar)
        addChangeListeners()
    }

    fun onClickSave(view: View) {
        saveData()
//        writeData()
        goToSavePage()
    }

    fun goToSavePage() {
        val i = Intent(this@EnterTimeActivity, ConfirmSave::class.java)
        startActivity(i)
    }

    fun saveData() {

//        var startTimePicker = findViewById<TimePicker>(R.id.enterData_startTimePicker)
//        var stopTimePicker = findViewById<TimePicker>(R.id.enterData_stopTimePicker)

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

    private fun writeData() {
        ToSheets.addTransaction(SessionData.Singleton.instance.currentCustomer, "Entered", applicationContext)
    }

    private fun addChangeListeners() {
//        var datePicker: CalendarView = findViewById(R.id.enterData_datePicker)
//        datePicker.setOnDateChangeListener { view, year, month, dayOfMonth ->
//            dateYear = year
//            dateMonth = month
//            dateDay = dayOfMonth
//        }

        var startTimePicker = findViewById<TimePicker>(R.id.enterData_startTimePicker)
        startTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            startHour = hourOfDay
            startMin = minute
        }

        var stopTimePicker = findViewById<TimePicker>(R.id.enterData_stopTimePicker)
        stopTimePicker.setOnTimeChangedListener { view, hourOfDay, minute ->
            stopHour = hourOfDay
            stopMin = minute
        }
    }
}
