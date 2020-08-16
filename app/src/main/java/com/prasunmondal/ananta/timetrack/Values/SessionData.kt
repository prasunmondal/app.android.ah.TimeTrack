package com.prasunmondal.ananta.timetrack.Values

class SessionData {

    object Singleton {
        val instance = SessionData()
    }

    var uniqueDeviceID = ""
    var systemInfo = ""
    lateinit var currentCustomer: Customer
}

class Customer {
    var name: String = ""
    var phoneNumber = ""
    var address = ""
    var latestStartTime = 0L
    var latestEndTime = 0L
    var pricePerUnit = 0F
    var prevBal = 0F
    var startTime = ""
    var endTime = ""
    var totalTime =  ""

    constructor(name: String, phoneNumber: String, address: String) {
        this.name = name
        this.phoneNumber = phoneNumber
        this.address = address
    }

    fun startTimer() {
        latestStartTime = System.currentTimeMillis()
    }

    fun stopTimer() {
        latestEndTime = System.currentTimeMillis()
    }

    fun getTimerValue(): Long {
        return latestEndTime - latestStartTime
    }

    fun getCalculatedPrice(): Float {
        return (this.latestEndTime - this.latestStartTime) * pricePerUnit
    }
}