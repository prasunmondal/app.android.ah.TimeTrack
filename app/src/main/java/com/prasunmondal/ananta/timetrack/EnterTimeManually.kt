package com.prasunmondal.ananta.timetrack

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity

import kotlinx.android.synthetic.main.activity_enter_time_manually.*

class EnterTimeManually : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_enter_time_manually)
        setSupportActionBar(toolbar)

    }

}
