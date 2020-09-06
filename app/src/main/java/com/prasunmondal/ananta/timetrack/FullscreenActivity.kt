package com.prasunmondal.ananta.timetrack

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
import com.prasunmondal.lib.android.deviceinfo.Device
import com.prasunmondal.lib.android.deviceinfo.DeviceInfo
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

        initiallize()
        Handler().postDelayed({ // This method will be executed once the timer is over
            val i = Intent(this@FullscreenActivity, SelectCustomer::class.java)
            startActivity(i)
            finish()
        }, 2000)

        ToSheets.logs.post(listOf(DeviceInfo.getAllInfo(), "Application Started"), this)
    }

    private fun initiallize() {
        DeviceInfo.setContext(this, contentResolver)
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
}
