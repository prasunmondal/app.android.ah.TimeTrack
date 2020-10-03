package com.prasunmondal.ananta.timetrack.Utility.PostToSheet

import android.content.Context
import com.prasunmondal.ananta.timetrack.utils.TimeUtils
import com.prasunmondal.ananta.timetrack.values.Customer
import com.prasunmondal.lib.android.deviceinfo.Device
import com.prasunmondal.lib.android.deviceinfo.DeviceInfo
import com.prasunmondal.lib.posttogsheets.PostToGSheet
import java.text.SimpleDateFormat
import java.util.*

class ToSheets private constructor() {


    companion object {

        val googleScript_scriptURL =
            "https://script.google.com/macros/s/AKfycbyoYcCSDEbXuDuGf0AhQjEi61ECAkl8JUv4ffNofz1yBIKfcT4/exec"

        val devDB = "https://docs.google.com/spreadsheets/d/1CvGQnFZL9YpUm1Ws_PtKFW_K8NVmm3OpEUZTwmfT4DA/edit#gid=0"
        val userDB = "https://docs.google.com/spreadsheets/d/1gZA5tqllOArlLJb2nLcmLqfNR-cdgFzNqTl9ZKyzcOI/edit#gid=0"

        // user profile
        val userLogs_sheet = devDB
        val userLogs_tab = "userLogs"
        val userErrors_sheet = devDB
        val userErrors_tab = "userErrors"
        val userAddTransactionSheet = userDB
        val userAddTransactionTab = "Transactions"


        // dev profile
        val devLogs_sheet = devDB
        val devLogs_tab = "devLogs"
        val devErrors_sheet = devDB
        val devErrors_tab = "devErrors"
        val devAddTransactionSheet = devDB
        val devAddTransactionTab = "Transactions"

        lateinit var logs: PostToGSheet
        lateinit var errors: PostToGSheet
        lateinit var addTransaction: PostToGSheet

        fun addTransaction(c: Customer, type: String, context: Context) {
            var postTo = this.addTransaction
            if (type == "Entered") {
                postTo = this.addTransaction
            }
            if (type == "Calculated") {
                postTo = this.addTransaction

            }

            val d = Calendar.getInstance().time
            println("Current time => $d")

            val df = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val formattedDate: String = df.format(d)
            postTo.post(
                listOf(
                    c.name,
                    c.phoneNumber,
                    c.address,
                    c.startTime,
                    c.endTime,
                    formattedDate,
                    TimeUtils.msToString(c.getTimeDiff()),
                    c.pricePerUnit.toString(),
                    c.getCalculatedPrice().toString(),
                    "DEBIT",
                    type,
                    DeviceInfo.get(Device.UNIQUE_ID)
                ), context
            )
        }
    }
}