package com.prasunmondal.ananta.timetrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.Models.InputType
import com.prasunmondal.ananta.timetrack.utils.TimeUtils
import com.prasunmondal.ananta.timetrack.values.SessionData

import kotlinx.android.synthetic.main.activity_enter_time_manually.*
import java.lang.Exception

class EnterTimeManually : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_time_manually)
        setSupportActionBar(toolbar)
    }

    fun onClickSave(view: View) {
        if(isValidInput()) {
            saveData()
            goToSavePage()
        } else {
            Toast.makeText(this, "Invalid Time Entered.", Toast.LENGTH_LONG).show()
        }
    }

    private fun saveData() {
        var hours = findViewById<EditText>(R.id.enter_time_manually_hour).text.toString()
        var minutes = findViewById<EditText>(R.id.enter_time_manually_min).text.toString()
        var temphours = 0
        var tempmins = 0
        if(hours.length != 0)
            temphours = hours.toInt()
        if(minutes.length != 0)
            tempmins = minutes.toInt()
        SessionData.Singleton.instance.currentCustomer.latestStartTime =
            TimeUtils.hrMinToMs(0, 0)
        SessionData.Singleton.instance.currentCustomer.latestEndTime =
            TimeUtils.hrMinToMs(temphours, tempmins)
    }

    private fun goToSavePage() {
        SessionData.Singleton.instance.currentCustomer.inputTypeIsManual = InputType.ENTERED_TOTAL
        val i = Intent(this@EnterTimeManually, ConfirmSave::class.java)
        startActivity(i)
    }

    private fun isValidInput(): Boolean {
        var hours = findViewById<EditText>(R.id.enter_time_manually_hour).text.toString()
        var minutes = findViewById<EditText>(R.id.enter_time_manually_min).text.toString()
        try {
            if(hours.length == 0 && minutes.length == 0)
                return false;

            var temphours = 0
            var tempmins = 0
            if(hours.length != 0)
                temphours = hours.toInt()
            if(minutes.length != 0)
                tempmins = minutes.toInt()
            if(temphours == 0 && tempmins == 0)
                return false
            if(temphours < 0 || tempmins < 0 || tempmins > 59)
                return false
        } catch (e: Exception) {
            return false
        }
        return true
    }

}
