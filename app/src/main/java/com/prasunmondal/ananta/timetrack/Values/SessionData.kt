package com.prasunmondal.ananta.timetrack.Values

class SessionData {

    object Singleton {
        val instance = SessionData()
    }

    var uniqueDeviceID = ""
    var systemInfo = ""
    var currentCustomer: Customer? = null
}

class Customer {
    var name: String = ""
    var phoneNumber = ""
    var address = ""

    constructor(name: String, phoneNumber: String, address: String) {
        this.name = name
        this.phoneNumber = phoneNumber
        this.address = address
    }
}