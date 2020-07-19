package com.prasunmondal.ananta.timetrack.Values

class SessionData {

    object Singleton {
        val instance = SessionData()
    }

    var uniqueDeviceID = ""
    var systemInfo = ""
}