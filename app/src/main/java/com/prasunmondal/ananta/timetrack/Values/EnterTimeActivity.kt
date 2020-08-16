package com.prasunmondal.ananta.timetrack.Values

import android.os.Bundle
import android.view.View
import android.widget.CalendarView
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.R
import kotlinx.android.synthetic.main.activity_enter_time.*

class EnterTimeActivity : AppCompatActivity() {
    var startHour:Int = 0
    var startMin:Int = 0
    var stopHour:Int = 0
    var stopMin:Int = 0
    var dateYear:Int = 0
    var dateMonth:Int = 0
    var dateDay:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_time)
        setSupportActionBar(toolbar)
        addChangeListeners()
    }

    fun onClickSave(view: View) {
        saveData()
    }

    fun saveData() {
        println("$dateYear $dateMonth $dateDay")
        println("$startHour $startMin")
        println("$stopHour $stopMin")
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
