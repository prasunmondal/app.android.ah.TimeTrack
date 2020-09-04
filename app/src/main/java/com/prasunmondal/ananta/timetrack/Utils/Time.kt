package com.prasunmondal.ananta.timetrack.Utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.abs

class TimeUtils {
    companion object {
        fun diff(start: Long, stop: Long): Long {
            return abs(stop - start)
        }

        fun msToString(ms: Long): String {
            val seconds = ms / 1000
            val hours = seconds / 3600
            val minutes = seconds % 3600 / 60
            val secs = seconds % 60

            val time = java.lang.String
                .format(
                    Locale.getDefault(),
                    "%d:%02d:%02d", hours, minutes, secs)
            return "seconds: $seconds - $time"
        }

        @SuppressLint("SimpleDateFormat")
        fun hrMinToMs(hour: Int, minutes: Int): Long {
            val currentDate: String =
                SimpleDateFormat("yyyy/MM/dd").format(Date())
            val myDate = "$currentDate 00:00:00"
            val sdf = SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
            val date = sdf.parse(myDate)
            val millis = date.time

            val min: Long = (hour * 60 + minutes).toLong()
            val sec = min * 60
            val ms = sec * 1000
            return ms + millis
        }
    }
}