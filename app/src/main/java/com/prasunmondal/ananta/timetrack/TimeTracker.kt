package com.prasunmondal.ananta.timetrack

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_time_tracker.*
import java.lang.String
import java.text.SimpleDateFormat
import java.util.*


class TimeTracker : AppCompatActivity() {

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
        if(running) {
            dateFormat.timeZone = TimeZone.getTimeZone("IST")
            start = sdf.format(Date())
            onClickReset(view)
            onClickStart(view)
        } else {
            stop = sdf.format(Date())
            onClickStop(view)
            println("$start $stop")
        }
    }

    // Start the stopwatch running
    // when the Start button is clicked.
    // Below method gets called
    // when the Start button is clicked.
    fun onClickStart(view: View?) {
        running = true
    }

    // Stop the stopwatch running
    // when the Stop button is clicked.
    // Below method gets called
    // when the Stop button is clicked.
    fun onClickStop(view: View?) {
        running = false
    }

    // Reset the stopwatch when
    // the Reset button is clicked.
    // Below method gets called
    // when the Reset button is clicked.
    fun onClickReset(view: View?) {
        running = false
        seconds = 0
    }

    // Sets the NUmber of seconds on the timer.
    // The runTimer() method uses a Handler
    // to increment the seconds and
    // update the text view.
    private fun runTimer() {

        // Get the text view.
        val timeView = findViewById(
            R.id.textView
        ) as TextView

        // Creates a new Handler
        val handler = Handler()

        // Call the post() method,
        // passing in a new Runnable.
        // The post() method processes
        // code without a delay,
        // so the code in the Runnable
        // will run almost immediately.
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

                // If running is true, increment the
                // seconds variable.
                if (running) {
                    seconds++
                }

                // Post the code again
                // with a delay of 1 second.
                handler.postDelayed(this, 1000)
            }
        })
    }
}
