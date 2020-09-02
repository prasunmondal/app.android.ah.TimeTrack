package com.prasunmondal.ananta.timetrack.Utility.PostToSheet

import android.content.Context
import com.prasunmondal.ananta.timetrack.Values.Customer
import com.prasunmondal.lib.posttogsheets.PostToGSheet

class ToSheets private constructor() {


    companion object {

        val googleScript_scriptURL =
            "https://script.google.com/macros/s/AKfycbyoYcCSDEbXuDuGf0AhQjEi61ECAkl8JUv4ffNofz1yBIKfcT4/exec"
        val sheet_output_URL =
            "https://docs.google.com/spreadsheets/d/1gZA5tqllOArlLJb2nLcmLqfNR-cdgFzNqTl9ZKyzcOI/edit#gid=0" // project_Ananta
        val sheet_devlogs_URL =
            "https://docs.google.com/spreadsheets/d/1nMItEbUTq8do0XZDOWr5gsTugwSxqoWPfCw6yqbroNw/edit#gid=0" // Project_Ananta_devlogs
        val sheet_devlogs_tabName = "run_logs"
        val logs: PostToGSheet =
            PostToGSheet(
                googleScript_scriptURL,
                sheet_output_URL,
                "logs",
                "https://docs.google.com/spreadsheets/d/1qacLjDP01fA5xxo1RNI9oGDyP6iknMQyIOPx24brJlA/edit#gid=0",
                "template",
                true, null
            )

        val error: PostToGSheet =
            PostToGSheet(
                googleScript_scriptURL,
                sheet_output_URL,
                "errors",
                "https://docs.google.com/spreadsheets/d/1qacLjDP01fA5xxo1RNI9oGDyP6iknMQyIOPx24brJlA/edit#gid=0",
                "template",
                true, null
            )

        val addTransaction_Calculated: PostToGSheet =
            PostToGSheet(
                googleScript_scriptURL,
                sheet_output_URL,
                "Data",
                "https://docs.google.com/spreadsheets/d/1qacLjDP01fA5xxo1RNI9oGDyP6iknMQyIOPx24brJlA/edit#gid=0",
                "template",
                true, null
            )

        val addTransaction_Entered: PostToGSheet =
            PostToGSheet(
                googleScript_scriptURL,
                sheet_output_URL,
                "Data",
                "https://docs.google.com/spreadsheets/d/1qacLjDP01fA5xxo1RNI9oGDyP6iknMQyIOPx24brJlA/edit#gid=0",
                "template",
                true, null
            )

        fun addTransaction(c: Customer, type: String, context: Context) {
            var postTo = addTransaction_Calculated
            if (type == "Entered") {
                postTo = addTransaction_Calculated
            }
            if (type == "Calculated") {
                postTo = addTransaction_Entered

            }
            postTo.post(
                listOf(
                    type,
                    c.name,
                    c.phoneNumber,
                    c.address,
                    c.startTime,
                    c.endTime,
                    c.totalTime,
                    c.pricePerUnit.toString(),
                    c.prevBal.toString()
                ), context
            )
        }
    }
}