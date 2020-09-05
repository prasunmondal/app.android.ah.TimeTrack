package com.prasunmondal.ananta.timetrack

import GetDeviceInfo.Device
import GetDeviceInfo.DeviceInfo
import android.content.Context
import android.content.Intent
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.Utility.PostToSheet.SelectCustomer
import com.prasunmondal.ananta.timetrack.Utility.PostToSheet.ToSheets
import kotlinx.android.synthetic.main.activity_fullscreen.*
import java.util.*
import com.prasunmondal.ananta.timetrack.values.SessionData.Singleton.instance as sessionData

class FullscreenActivity : AppCompatActivity() {
    private val mHideHandler = Handler()
    private val mHidePart2Runnable = Runnable {
    }
    private val mShowPart2Runnable = Runnable {
        // Delayed display of UI elements
        supportActionBar?.show()
        fullscreen_content_controls.visibility = View.VISIBLE
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mVisible = true

        Handler().postDelayed({ // This method will be executed once the timer is over
            val i = Intent(this@FullscreenActivity, SelectCustomer::class.java)
            startActivity(i)
            finish()
        }, 2000)

        populateSystemInfo()
        ToSheets.logs.post(listOf(sessionData.systemInfo, "Application Started"), this)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        delayedHide(100)
    }

    private fun hide() {
        // Hide UI first
        supportActionBar?.hide()
        fullscreen_content_controls.visibility = View.GONE
        mVisible = false

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable)
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun show() {
        mVisible = true
        mHideHandler.removeCallbacks(mHidePart2Runnable)
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    companion object {
        private val UI_ANIMATION_DELAY = 300
    }

    fun populateSystemInfo() {
        sessionData.systemInfo = generateDeviceId() + " - "
        sessionData.systemInfo += DeviceInfo.getDeviceInfo(
            applicationContext,
            Device.DEVICE_UNIQUE_ID
        ) + " - "
        sessionData.systemInfo += DeviceInfo.getDeviceInfo(
            applicationContext,
            Device.DEVICE_MAC_ADDRESS
        )
        sessionData.systemInfo += ", " + DeviceInfo.getDeviceInfo(
            applicationContext,
            Device.DEVICE_IN_INCH
        )
        sessionData.systemInfo += ", " + DeviceInfo.getDeviceInfo(
            applicationContext,
            Device.DEVICE_HARDWARE_MODEL
        )
        sessionData.systemInfo += ", " + DeviceInfo.getDeviceInfo(
            applicationContext,
            Device.DEVICE_NUMBER_OF_PROCESSORS
        )
        sessionData.systemInfo += ", " + DeviceInfo.getDeviceInfo(
            applicationContext,
            Device.DEVICE_SYSTEM_NAME
        )
        sessionData.systemInfo += ", " + DeviceInfo.getDeviceInfo(
            applicationContext,
            Device.DEVICE_VERSION
        )
    }

    fun generateDeviceId(): String {
        val macAddr: String
        val wifiMan =
            this.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val wifiInf = wifiMan.connectionInfo
        macAddr = wifiInf.macAddress
        val androidId: String = "" + Settings.Secure.getString(
            contentResolver,
            Settings.Secure.ANDROID_ID
        )
        val deviceUuid = UUID(androidId.hashCode().toLong(), macAddr.hashCode().toLong())
        return deviceUuid.toString()
    }
}
