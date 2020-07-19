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
import com.prasunmondal.ananta.timetrack.Utility.PostToSheet.ToSheet
import com.prasunmondal.ananta.timetrack.Values.SessionData.Singleton.instance as sessionData
import kotlinx.android.synthetic.main.activity_fullscreen.*
import java.util.*

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenActivity : AppCompatActivity() {
    private val mHideHandler = Handler()
    private val mHidePart2Runnable = Runnable {
        // Delayed removal of status and navigation bar

        // Note that some of these constants are new as of API 16 (Jelly Bean)
        // and API 19 (KitKat). It is safe to use them, as they are inlined
        // at compile-time and do nothing on earlier devices.
//        fullscreen_content.systemUiVisibility =
//            View.SYSTEM_UI_FLAG_LOW_PROFILE or
//                    View.SYSTEM_UI_FLAG_FULLSCREEN or
//                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE or
//                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY or
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION or
//                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
    }
    private val mShowPart2Runnable = Runnable {
        // Delayed display of UI elements
        supportActionBar?.show()
        fullscreen_content_controls.visibility = View.VISIBLE
    }
    private var mVisible: Boolean = false
    private val mHideRunnable = Runnable { hide() }

    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private val mDelayHideTouchListener = View.OnTouchListener { _, _ ->
        if (AUTO_HIDE) {
            delayedHide(AUTO_HIDE_DELAY_MILLIS)
        }
        false
    }

    fun goToCountDown() {
        val i = Intent(this@FullscreenActivity, TimeTracker::class.java)
        startActivity(i)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_fullscreen)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mVisible = true

        Handler().postDelayed({ // This method will be executed once the timer is over
            val i = Intent(this@FullscreenActivity, TimeTracker::class.java)
            startActivity(i)
            finish()
        }, 1500)

        populateSystemInfo()
        ToSheet().log(sessionData.systemInfo, "Application Started", this)
        // Set up the user interaction to manually show or hide the system UI.
//        fullscreen_content.setOnClickListener { toggle() }

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
//        dummy_button.setOnTouchListener(mDelayHideTouchListener)

//        sleep
//        goToCountDown()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100)
    }

    private fun toggle() {
        if (mVisible) {
            hide()
        } else {
            show()
        }
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
        // Show the system bar
//        fullscreen_content.systemUiVisibility =
//            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
//                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
        mVisible = true

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable)
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY.toLong())
    }

    /**
     * Schedules a call to hide() in [delayMillis], canceling any
     * previously scheduled calls.
     */
    private fun delayedHide(delayMillis: Int) {
        mHideHandler.removeCallbacks(mHideRunnable)
        mHideHandler.postDelayed(mHideRunnable, delayMillis.toLong())
    }

    companion object {
        /**
         * Whether or not the system UI should be auto-hidden after
         * [AUTO_HIDE_DELAY_MILLIS] milliseconds.
         */
        private val AUTO_HIDE = true

        /**
         * If [AUTO_HIDE] is set, the number of milliseconds to wait after
         * user interaction before hiding the system UI.
         */
        private val AUTO_HIDE_DELAY_MILLIS = 3000

        /**
         * Some older devices needs a small delay between UI widget updates
         * and a change of the status and navigation bar.
         */
        private val UI_ANIMATION_DELAY = 300
    }

    fun populateSystemInfo() {
        sessionData.systemInfo = generateDeviceId() + " - "
//        sessionData.systemInfo = "System-infos: "
//        sessionData.systemInfo += "     OS Version: " + System.getProperty("os.version") + "(" + android.os.Build.VERSION.INCREMENTAL + ")"
//        sessionData.systemInfo += "     OS API Level: " + Build.VERSION.SDK_INT
//        sessionData.systemInfo += "     Device: " + Build.DEVICE
//        sessionData.systemInfo += "     Model (and Product): " + Build.MODEL + " ("+ Build.PRODUCT + ")"
//        sessionData.systemInfo += "      windowHeight: " + window.windowManager.defaultDisplay.height
//        sessionData.systemInfo += "      windowWidth(): " + window.windowManager.defaultDisplay.width
//        sessionData.systemInfo += "      generateDeviceId(): " + generateDeviceId()


        sessionData.systemInfo += DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_UNIQUE_ID) + " - "

//        sessionData.systemInfo += "\n" + System.getProperty("os.name")
//        sessionData.systemInfo += "\n" + System.getProperty("os.version")
//        sessionData.systemInfo += "\n" + Build.VERSION.RELEASE
//        sessionData.systemInfo += "\n" + Build.DEVICE
//        sessionData.systemInfo += "\n" + Build.MODEL
//        sessionData.systemInfo += "\n" + Build.PRODUCT
//        sessionData.systemInfo += "\n" + Build.BRAND
//        sessionData.systemInfo += "\n" + Build.DISPLAY
//        sessionData.systemInfo += "\n" + Build.CPU_ABI
//        sessionData.systemInfo += "\n" + Build.CPU_ABI2
//        sessionData.systemInfo += "\n" + Build.UNKNOWN
//        sessionData.systemInfo += "\n" + Build.HARDWARE
//        sessionData.systemInfo += "\n" + Build.ID
//        sessionData.systemInfo += "\n" + Build.MANUFACTURER
//        sessionData.systemInfo += "\n" + Build.SERIAL
//        sessionData.systemInfo += "\n" + Build.USER
//        sessionData.systemInfo += "\n" + Build.HOST

//        sessionData.systemInfo += "\n" + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_CURRENT_DATE_TIME)
//        sessionData.systemInfo += "\n" + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_CURRENT_DATE_TIME_ZERO_GMT)
//        sessionData.systemInfo += "\n" + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_CURRENT_YEAR)
//        sessionData.systemInfo += "\n" + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_FREE_MEMORY)
//        sessionData.systemInfo += "\n" + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_HARDWARE_MODEL)
//        sessionData.systemInfo += "\n" + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_IN_INCH)
//        sessionData.systemInfo += "\n" + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_IP_ADDRESS_IPV4)
//        sessionData.systemInfo += "\n" + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_IP_ADDRESS_IPV6)
//        sessionData.systemInfo += "\n" + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_LANGUAGE)
//        sessionData.systemInfo += "\n" + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_MAC_ADDRESS)
//        sessionData.systemInfo += "\n" + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_NAME)

        sessionData.systemInfo += DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_MAC_ADDRESS)
        sessionData.systemInfo += ", " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_IN_INCH)
        sessionData.systemInfo += ", " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_HARDWARE_MODEL)
        sessionData.systemInfo += ", " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_NUMBER_OF_PROCESSORS)
        sessionData.systemInfo += ", " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_SYSTEM_NAME)
        sessionData.systemInfo += ", " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_VERSION)

//        sessionData.systemInfo += "\nDEVICE_TYPE: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_TYPE)
//
//        sessionData.systemInfo += "\nDEVICE_SYSTEM_VERSION: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_SYSTEM_VERSION)
//        sessionData.systemInfo += "\nDEVICE_TOKEN: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_TOKEN)
//        sessionData.systemInfo += "\nDEVICE_NAME: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_NAME)
//        sessionData.systemInfo += "\nDEVICE_UUID: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_UUID)
//        sessionData.systemInfo += "\nDEVICE_MANUFACTURE: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_MANUFACTURE)
//        sessionData.systemInfo += "\nCONTACT_ID: " + DeviceInfo.getDeviceInfo(applicationContext, Device.CONTACT_ID)
//        sessionData.systemInfo += "\nDEVICE_LANGUAGE: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_LANGUAGE)
//        sessionData.systemInfo += "\nDEVICE_TIME_ZONE: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_TIME_ZONE)
//        sessionData.systemInfo += "\nDEVICE_LOCAL_COUNTRY_CODE: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_LOCAL_COUNTRY_CODE)
//        sessionData.systemInfo += "\nDEVICE_CURRENT_YEAR: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_CURRENT_YEAR)
//        sessionData.systemInfo += "\nDEVICE_CURRENT_DATE_TIME: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_CURRENT_DATE_TIME)
//        sessionData.systemInfo += "\nDEVICE_CURRENT_DATE_TIME_ZERO_GMT: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_CURRENT_DATE_TIME_ZERO_GMT)
//
//        sessionData.systemInfo += "\nDEVICE_LOCALE: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_LOCALE)
//        sessionData.systemInfo += "\nDEVICE_NETWORK: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_NETWORK)
//        sessionData.systemInfo += "\nDEVICE_NETWORK_TYPE: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_NETWORK_TYPE)
//        sessionData.systemInfo += "\nDEVICE_IP_ADDRESS_IPV4: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_IP_ADDRESS_IPV4)
//        sessionData.systemInfo += "\nDEVICE_IP_ADDRESS_IPV6: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_IP_ADDRESS_IPV6)
//
//        sessionData.systemInfo += "\nDEVICE_TOTAL_CPU_USAGE: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_TOTAL_CPU_USAGE)
//        sessionData.systemInfo += "\nDEVICE_TOTAL_MEMORY: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_TOTAL_MEMORY)
//        sessionData.systemInfo += "\nDevice: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_FREE_MEMORY)
//        sessionData.systemInfo += "\nDEVICE_USED_MEMORY: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_USED_MEMORY)
//        sessionData.systemInfo += "\nDEVICE_TOTAL_CPU_USAGE_USER: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_TOTAL_CPU_USAGE_USER)
//        sessionData.systemInfo += "\nDEVICE_TOTAL_CPU_USAGE_SYSTEM: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_TOTAL_CPU_USAGE_SYSTEM)
//        sessionData.systemInfo += "\nDEVICE_TOTAL_CPU_IDLE: " + DeviceInfo.getDeviceInfo(applicationContext, Device.DEVICE_TOTAL_CPU_IDLE)
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
