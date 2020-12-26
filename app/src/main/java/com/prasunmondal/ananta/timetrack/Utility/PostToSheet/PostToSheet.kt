package com.prasunmondal.ananta.timetrack.Utility.PostToSheet

import android.content.Context
import com.prasunmondal.ananta.timetrack.Models.InputType
import com.prasunmondal.ananta.timetrack.utils.TimeUtils
import com.prasunmondal.ananta.timetrack.values.Customer
import com.prasunmondal.lib.android.deviceinfo.Device
import com.prasunmondal.lib.android.deviceinfo.DeviceInfo
import com.prasunmondal.lib.posttogsheets.PostToGSheet
import java.text.SimpleDateFormat
import java.util.*

class ToSheets private constructor() {


    companion object {

        const val googleScript_scriptURL =
            "https://script.google.com/macros/s/AKfycbyoYcCSDEbXuDuGf0AhQjEi61ECAkl8JUv4ffNofz1yBIKfcT4/exec"
        private const val devDB: String = "https://docs.google.com/spreadsheets/d/1CvGQnFZL9YpUm1Ws_PtKFW_K8NVmm3OpEUZTwmfT4DA/edit#gid=0"
        private const val userDB: String = "https://docs.google.com/spreadsheets/d/1gZA5tqllOArlLJb2nLcmLqfNR-cdgFzNqTl9ZKyzcOI/edit#gid=0"

        // user profile
        const val userLogs_sheet = devDB
        const val userLogs_tab = "userLogs"
        const val userErrors_sheet = devDB
        const val userErrors_tab = "userErrors"
        const val userAddTransactionSheet = userDB
        const val userAddTransactionTab = "Transactions"

        // dev profile
        const val devLogs_sheet = devDB
        const val devLogs_tab = "devLogs"
        const val devErrors_sheet = devDB
        const val devErrors_tab = "devErrors"
        const val devAddTransactionSheet = devDB
        const val devAddTransactionTab = "Transactions"

        lateinit var logs: PostToGSheet
        lateinit var errors: PostToGSheet
        lateinit var addTransaction: PostToGSheet

        fun addTransaction(c: Customer, type: InputType, context: Context) {
            var postTo = this.addTransaction

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
                    type.toString(),
                    DeviceInfo.get(Device.UNIQUE_ID)
                ), context
            )
        }
    }
}