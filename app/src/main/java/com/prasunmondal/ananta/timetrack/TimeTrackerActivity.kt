package com.prasunmondal.ananta.timetrack

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.Utility.PostToSheet.ToSheets
import kotlinx.android.synthetic.main.activity_time_tracker.*
import java.lang.String
import java.text.SimpleDateFormat
import java.util.*
import com.prasunmondal.ananta.timetrack.values.SessionData.Singleton.instance as sessionData


class TimeTrackerActivity : AppCompatActivity() {

    // Use seconds, running and wasRunning respectively
    // to record the number of seconds passed,
    // whether the stopwatch is running and
    // whether the stopwatch was running
    // before the activity was paused.

    // Use seconds, running and wasRunning respectively
    // to record the number of seconds passed,
    // whether the stopwatch is running and
    // whether the stopwatch was running
    // before the activity was paused.
    // Number of seconds displayed
    // on the stopwatch.
    private var seconds = 0

    // Is the stopwatch running?
    private var running = false

    private var wasRunning = false

    val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    val format = "yyyy-MM-dd HH:mm:ss:SSS"
    val sdf = SimpleDateFormat(format)
    var start = ""
    var stop = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_time_tracker)
        setSupportActionBar(toolbar)

        if (savedInstanceState != null) {

            // Get the previous state of the stopwatch
            // if the activity has been
            // destroyed and recreated.
            seconds = savedInstanceState
                .getInt("seconds")
            running = savedInstanceState
                .getBoolean("running")
            wasRunning = savedInstanceState
                .getBoolean("wasRunning")
        }
        runTimer()
    }

    // Save the state of the stopwatch
    // if it's about to be destroyed.
    @SuppressLint("MissingSuperCall")
    override fun onSaveInstanceState(
        savedInstanceState: Bundle
    ) {
        savedInstanceState
            .putInt("seconds", seconds)
        savedInstanceState
            .putBoolean("running", running)
        savedInstanceState
            .putBoolean("wasRunning", wasRunning)
    }

    // If the activity is paused,
    // stop the stopwatch.
    override fun onPause() {
        super.onPause()
        wasRunning = running
        running = false
    }

    // If the activity is resumed,
    // start the stopwatch
    // again if it was running previously.
    override fun onResume() {
        super.onResume()
        if (wasRunning) {
            running = true
        }
    }

    fun toggleTimer(view: View) {
        running = !running
        if (running) {
            dateFormat.timeZone = TimeZone.getTimeZone("IST")
            start = sdf.format(Date())
            sessionData.currentCustomer.startTimer()
            findViewById<Button>(R.id.btn_startStop).text = "Stop"
            onClickReset(view)
            onClickStart(view)
            ToSheets.logs.post(
                listOf(
                    sessionData.systemInfo,
                    "Started - ms: " + sessionData.currentCustomer.latestStartTime.toString() + " TimeStamp: " + start
                ), this
            )
        } else {
            stop = sdf.format(Date())
            sessionData.currentCustomer.stopTimer()
            findViewById<Button>(R.id.btn_startStop).text = "Start"
            onClickStop(view)
            println("$start $stop")

            seconds = (sessionData.currentCustomer.getTimerValue() / 1000).toInt()
            val hours = seconds / 3600
            val minutes = seconds % 3600 / 60
            val secs = seconds % 60

            val timeView = findViewById(
                R.id.textView
            ) as TextView
            // Format the seconds into hours, minutes,
            // and seconds.
            val time = String
                .format(
                    Locale.getDefault(),
                    "%d:%02d:%02d", hours,
                    minutes, secs
                )

            // Set the text view text.
            timeView.text = time

            sessionData.currentCustomer.startTime = start
            sessionData.currentCustomer.endTime = stop
            sessionData.currentCustomer.totalTime = time

//                    writeData()
            goToSavePage()
        }

    }

    fun goToSavePage() {
        sessionData.currentCustomer.inputTypeIsManual = false
        val i = Intent(this@TimeTrackerActivity, ConfirmSave::class.java)
        startActivity(i)
    }

    fun onClickStart(view: View?) {
        running = true
    }

    fun onClickStop(view: View?) {
        running = false
    }

    fun onClickReset(view: View?) {
        running = false
        seconds = 0
    }

    private fun runTimer() {

        val timeView = findViewById(
            R.id.textView
        ) as TextView

        val handler = Handler()

        handler.post(object : Runnable {
            override fun run() {

                val hours = seconds / 3600
                val minutes = seconds % 3600 / 60
                val secs = seconds % 60

                // Format the seconds into hours, minutes,
                // and seconds.
                val time = String
                    .format(
                        Locale.getDefault(),
                        "%d:%02d:%02d", hours,
                        minutes, secs
                    )

                // Set the text view text.
                timeView.text = time

                if (running) {
                    seconds =
                        ((System.currentTimeMillis() - sessionData.currentCustomer.latestStartTime) / 1000).toInt()
                }
                handler.postDelayed(this, 100)
            }
        })
    }
}
