package com.prasunmondal.ananta.timetrack.Utils

class TimeUtils {
    companion object {
        fun diff(start: Long, stop: Long): Long {
            return Math.abs(stop - start);
        }
    }
}