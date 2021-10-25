package com.example.giftapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.giftapp.ui.clock.ClockView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ClockView.ClockViewListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        clockView.clockViewListener = this
    }


    override fun onHourChanged() {
        TODO("Not yet implemented")
    }

    override fun onMinutChanged() {
        TODO("Not yet implemented")
    }

    override fun onSecondCgabed() {
        TODO("Not yet implemented")
    }
}
