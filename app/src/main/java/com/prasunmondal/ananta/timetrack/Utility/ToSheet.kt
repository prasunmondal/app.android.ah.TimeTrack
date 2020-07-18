package com.prasunmondal.ananta.timetrack.Utility

import android.content.Context
import com.prasunmondal.ananta.timetrack.BuildConfig
import com.prasunmondal.ananta.timetrack.Utility.PostToSheet.PostToSheet
import java.text.SimpleDateFormat
import java.util.*

class ToSheet {

    fun post(startTime: String, endTime: String, calculatedTime: String, context: Context) {

        var isDev = false

        if(isDev)
            return
        try {
            var appVersion = BuildConfig.VERSION_CODE.toString()

            val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
            val format = "yyyy-MM-dd HH:mm:ss:SSS"
            val sdf = SimpleDateFormat(format)
            dateFormat.timeZone = TimeZone.getTimeZone("IST")

            PostToSheet().post(context,
                "https://script.google.com/macros/s/AKfycbyoYcCSDEbXuDuGf0AhQjEi61ECAkl8JUv4ffNofz1yBIKfcT4/exec",
                "https://docs.google.com/spreadsheets/d/1qacLjDP01fA5xxo1RNI9oGDyP6iknMQyIOPx24brJlA/edit#gid=0",
                "Ananta",
            listOf(sdf.format(Date()), appVersion, startTime, endTime, calculatedTime))
        } catch (e: Exception) {

        }
    }
}
