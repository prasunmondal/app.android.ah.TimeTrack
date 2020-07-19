package com.prasunmondal.ananta.timetrack.Utility.PostToSheet

import android.content.Context
import com.prasunmondal.ananta.timetrack.BuildConfig
import com.prasunmondal.ananta.timetrack.Values.Constants
import java.text.SimpleDateFormat
import java.util.*

class ToSheet {

    fun output(startTime: String, endTime: String, calculatedTime: String, startMillis: String, stopMillis: String, context: Context) {

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
                Constants.Singleton.instance.googleScript_scriptURL,
                Constants.Singleton.instance.sheet_output_URL,
                Constants.Singleton.instance.sheet_output_name,
            listOf(sdf.format(Date()), appVersion, startTime, endTime, startMillis, stopMillis, (stopMillis.toLong() - startMillis.toLong()).toString(), calculatedTime))
        } catch (e: Exception) {

        }
    }

    fun log(deviceID: String, text: String, context: Context) {

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
                Constants.Singleton.instance.googleScript_scriptURL,
                Constants.Singleton.instance.sheet_devlogs_URL,
                Constants.Singleton.instance.sheet_devlogs_tabName,
                listOf(sdf.format(Date()), appVersion, deviceID, text))
        } catch (e: Exception) {

        }
    }
}
