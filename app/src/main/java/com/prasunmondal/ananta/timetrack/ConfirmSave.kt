package com.prasunmondal.ananta.timetrack

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.Models.InputType
import com.prasunmondal.ananta.timetrack.Utility.PostToSheet.SelectCustomer
import com.prasunmondal.ananta.timetrack.Utility.PostToSheet.ToSheets
import com.prasunmondal.ananta.timetrack.utils.LogActions
import com.prasunmondal.ananta.timetrack.utils.TimeUtils
import com.prasunmondal.ananta.timetrack.values.SessionData.Singleton.instance as session

import kotlinx.android.synthetic.main.activity_confirm_save.*

class ConfirmSave : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirm_save)
        setSupportActionBar(toolbar)

        if(session.currentCustomer.startTime == "") {
            ToSheets.logs.post(listOf(LogActions.CLICKED.name, "Confirm Save::" +
                    " ::Start time:" + session.currentCustomer.latestStartTime +
                    " ::Stop time:" + session.currentCustomer.latestEndTime +
                    " ::Total time:" + session.currentCustomer.getTimeDiff()
            ), this)
        } else {
            ToSheets.logs.post(
                listOf(
                    LogActions.CLICKED.name, "Confirm Save::" +
                            " ::Start time:" + session.currentCustomer.startTime +
                            " ::Stop time:" + session.currentCustomer.endTime +
                            " ::Total time:" + session.currentCustomer.totalTime
                ), this
            )
        }
        val confirmTimeView = findViewById<TextView>(R.id.confirmTimeView)
        val showString = TimeUtils.msToString(session.currentCustomer.getTimeDiff())
        confirmTimeView.text = showString
        println(showString)
    }

    fun onClickSave(view: View) {
        writeData()
        if(session.currentCustomer.startTime == "") {
            ToSheets.logs.post(listOf(LogActions.CLICKED.name, "SAVE::" +
                    " :Start time:" + session.currentCustomer.latestStartTime +
                    " :Stop time:" + session.currentCustomer.latestEndTime +
                    " :Total time:" + session.currentCustomer.getTimeDiff()
            ), this)
        } else {
            ToSheets.logs.post(
                listOf(
                    LogActions.CLICKED.name, "SAVE::" +
                            " :Start time:" + session.currentCustomer.startTime +
                            " :Stop time:" + session.currentCustomer.endTime +
                            " :Total time:" + session.currentCustomer.totalTime
                ), this
            )
        }
        Toast.makeText(this,"Data Saved!", Toast.LENGTH_LONG).show()
        val intent = Intent(this@ConfirmSave, SelectCustomer::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        startActivity(intent)
        finish()
    }

    private fun writeData() {
        ToSheets.addTransaction(session.currentCustomer, session.currentCustomer.inputTypeIsManual, applicationContext)
    }
}
