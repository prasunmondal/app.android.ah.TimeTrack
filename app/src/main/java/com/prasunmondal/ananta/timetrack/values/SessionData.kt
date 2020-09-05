package com.prasunmondal.ananta.timetrack.values

import com.prasunmondal.ananta.timetrack.utils.TimeUtils

class SessionData {

    object Singleton {
        val instance = SessionData()
    }

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
    var totalTime = ""
    var inputTypeIsManual = false

    companion object {
        var CSV_INDEX_NAME = 1
        var CSV_INDEX_CONTACTNO = 2
        var CSV_INDEX_ADDRESS = 3
        var CSV_INDEX_PRICE = 4
    }

    constructor(name: String, phoneNumber: String, address: String, price: Float) {
        this.name = name
        this.phoneNumber = phoneNumber
        this.address = address
        this.pricePerUnit = price
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

    fun getTimeDiff(): Long {
        return TimeUtils.diff(this.latestStartTime, this.latestEndTime)
    }
}