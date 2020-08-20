package com.mrspd.onsiteround2

import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    companion object {
       var sec = 0
        var min = 0
        var milliSec = 0
    }
    private var isResumed = false
    var handler: Handler? = null
    var mMillisecind: Long = 0
    var tStart: Long = 0
    var mLOL: Long = 0
    var mUpdatetime = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler = Handler()
        start.setOnClickListener(View.OnClickListener {
            if (!isResumed) {
                tStart = SystemClock.uptimeMillis()
                handler!!.postDelayed(runnable, 0)
                isResumed = true
                reset.setVisibility(View.INVISIBLE)
                start.text = "PAUSE"

            } else {
                mLOL += mMillisecind
                handler!!.removeCallbacks(runnable)
                isResumed = false
                reset.setVisibility(View.VISIBLE)
                start.text = "START"

            }
        })
        reset.setOnClickListener(View.OnClickListener {
            if (!isResumed) {
                start.text = "PAUSE"
                mMillisecind = 0L
                tStart = 0L
                mLOL = 0L
                mUpdatetime = 0L
                sec = 0
                min = 0
                milliSec = 0
            }
        })
    }

    var runnable: Runnable = object : Runnable {
        override fun run() {
            mMillisecind = SystemClock.uptimeMillis() - tStart
            mUpdatetime = mLOL + mMillisecind
            sec = (mUpdatetime / 1000).toInt()
            min = sec / 60
            sec %= 60
            milliSec = (mUpdatetime % 1000).toInt()
            handler!!.postDelayed(this, 60)
        }
    }


}