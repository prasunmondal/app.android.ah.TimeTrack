package com.prasunmondal.ananta.timetrack.utils

import com.prasunmondal.lib.android.deviceinfo.Device
import com.prasunmondal.lib.android.deviceinfo.DeviceInfo

class CommonUtils {
    fun isDevEnv(): Boolean {
        if(DeviceInfo.get(Device.UNIQUE_ID) == "prasun") {
            return true
        }
        return false
    }
}