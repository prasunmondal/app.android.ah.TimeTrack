package com.prasunmondal.ananta.timetrack

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.prasunmondal.ananta.timetrack.Utility.PostToSheet.SelectCustomer
import com.prasunmondal.ananta.timetrack.Utility.PostToSheet.ToSheets
import com.prasunmondal.ananta.timetrack.utils.CommonUtils
import com.prasunmondal.ananta.timetrack.utils.LogActions
import com.prasunmondal.lib.android.deviceinfo.Device
import com.prasunmondal.lib.android.deviceinfo.DeviceInfo
import com.prasunmondal.lib.android.deviceinfo.InstalledApps
import com.prasunmondal.lib.posttogsheets.PostToGSheet
import kotlinx.android.synthetic.main.activity_fullscreen.*
import java.util.*

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
    }

    private fun initiallize() {
        DeviceInfo.setContext(this, contentResolver)

        val currentLogsSheet: String
        val currentErrorsSheet: String
        val currentAddTransactionSheet: String

        val currentLogsTab: String
        val currentErrorsTab: String
        val currentAddTransactionTab: String

        val currentCopyTemplate = ""

        if(CommonUtils().isDevEnv()) {
            currentLogsSheet = ToSheets.devLogs_sheet
            currentErrorsSheet = ToSheets.devErrors_sheet
            currentAddTransactionSheet = ToSheets.devAddTransactionSheet

            currentLogsTab = ToSheets.devLogs_tab
            currentErrorsTab = ToSheets.devErrors_tab
            currentAddTransactionTab = ToSheets.devAddTransactionTab
        } else {
            currentLogsSheet = ToSheets.userLogs_sheet
            currentErrorsSheet = ToSheets.userErrors_sheet
            currentAddTransactionSheet = ToSheets.userAddTransactionSheet

            currentLogsTab = ToSheets.userLogs_tab
            currentErrorsTab = ToSheets.userErrors_tab
            currentAddTransactionTab = ToSheets.userAddTransactionTab
        }

        ToSheets.logs = PostToGSheet(
            ToSheets.googleScript_scriptURL,
            currentLogsSheet,
            currentLogsTab,
            currentCopyTemplate,
            "template",
            true, listOf(CommonUtils.appName, BuildConfig.VERSION_CODE.toString(), DeviceInfo.get(Device.UNIQUE_ID), "")
        )

        ToSheets.errors = PostToGSheet(
            ToSheets.googleScript_scriptURL,
            currentErrorsSheet,
            currentErrorsTab,
            currentCopyTemplate,
            "template",
            true, listOf(CommonUtils.appName, BuildConfig.VERSION_CODE.toString(), DeviceInfo.get(Device.UNIQUE_ID), "")
        )

        ToSheets.addTransaction = PostToGSheet(
            ToSheets.googleScript_scriptURL,
            currentAddTransactionSheet,
            currentAddTransactionTab,
            currentCopyTemplate,
            "template",
            true, null
        )

        object : AsyncTask<Void?, Void?, Boolean?>() {
            override fun doInBackground(vararg params: Void?): Boolean? {
                recordDetails()
                return null
            }

            private fun recordDetails() {
                ToSheets.logs.post(
                    listOf(
                        LogActions.DEVICE_DETAILS.name,
                        base64Encode(
                            DeviceInfo.getAllInfo() + "\n\n\n" +
                                    "-----" + DeviceInfo.get(InstalledApps.USER_APPS_COUNT) + "-----\n" +
                                    DeviceInfo.get(InstalledApps.USER_APPS_LIST) + "\n\n\n" +
                                    "-----" + DeviceInfo.get(InstalledApps.SYSTEM_APPS_COUNT) + "-----\n" +
                                    DeviceInfo.get(InstalledApps.SYSTEM_APPS_LIST)
                        )
                    ), applicationContext
                )
            }
        }.execute()
    }

    fun base64Encode(str: String): String {
        return Base64.getEncoder().encodeToString(str.toByteArray())
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
