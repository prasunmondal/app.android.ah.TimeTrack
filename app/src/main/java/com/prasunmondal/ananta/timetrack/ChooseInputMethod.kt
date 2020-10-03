package com.prasunmondal.ananta.timetrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.Utility.PostToSheet.ToSheets
import com.prasunmondal.ananta.timetrack.utils.LogActions
import com.prasunmondal.ananta.timetrack.values.EnterTimeActivity

import kotlinx.android.synthetic.main.activity_choose_input_method.*

class ChooseInputMethod : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_choose_input_method)
        setSupportActionBar(toolbar)
    }

    fun onClickTimeTrack(view: View) {
        ToSheets.logs.post(listOf(LogActions.CLICKED.name, "Select Input Type - Stopwatch"), this)
        goToTimeTrackActivity()
    }

    fun onClickEnterTime(view: View) {
        ToSheets.logs.post(listOf(LogActions.CLICKED.name, "Select Input Type - EnterTime"), this)
        goToEnterTimeActivity()
    }

    private fun goToTimeTrackActivity() {
        val i = Intent(this@ChooseInputMethod, TimeTrackerActivity::class.java)
        startActivity(i)
    }

    private fun goToEnterTimeActivity() {
        val i = Intent(this@ChooseInputMethod, EnterTimeActivity::class.java)
        startActivity(i)
    }
}