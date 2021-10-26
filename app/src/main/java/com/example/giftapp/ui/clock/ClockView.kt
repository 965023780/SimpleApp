package com.example.giftapp.ui.clock

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.example.giftapp.R
import com.example.giftapp.util.DisplayUtils
import java.sql.Time
import java.util.*

/**
 * creator: sothoth
 * date: 2021/10/18
 * description: 钟表
 */
class ClockView : ViewGroup {
    private val periodView by lazy {
        PeriodView(context)
    }

    private val hourView by lazy {
        HourDiskView(context)
    }

    private val minuteView by lazy {
        MinuteDiskView(context)
    }

    private val secondView by lazy{
        SecondDiskView(context)
    }

    private val clockTime by lazy {
        TextView(context)
    }

    private val holder by lazy{
        ClockViewHolder(this)
    }

    interface ClockViewListener {
        fun onHourChanged()

        fun onMinuteChanged()

        fun onSecondChanged()
    }

    lateinit var clockViewListener: ClockViewListener

    init {
        secondView.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        addView(secondView)

        minuteView.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        addView(minuteView)

        hourView.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        addView(hourView)

        periodView.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        addView(periodView)

        clockTime.layoutParams =
            LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        clockTime.textSize = DisplayUtils.dp2px(context, 30f).toFloat()
        clockTime.setTextColor(ContextCompat.getColor(context, R.color.clockTimeText))
        clockTime.text = "00:00"
        addView(clockTime)
        holder.startTimer()
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        for (childView in children) {
            measureChild(childView, widthMeasureSpec, heightMeasureSpec)
        }
        setMeasuredDimension(minuteView.measuredWidth, minuteView.measuredHeight)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for (childView in children) {
            when (childView) {
                is DiskView ->
                    childView.layout(0, 0, childView.measuredWidth, childView.measuredHeight)
                is TextView -> {
                    val left = (DisplayUtils.getScreenWidth(context) - childView.measuredWidth) / 2
                    val top = DisplayUtils.getScreenHeight(context) / 2
                    childView.layout(
                        left,
                        top,
                        left + childView.measuredWidth,
                        top + childView.measuredHeight
                    )
                }
            }
        }
    }

    @Synchronized
    fun setTime(curSecond: Int, curMinute: Int, curHour: Int){
        secondView.setTime(curSecond)
        minuteView.setTime(curMinute)
        hourView.setTime(curHour)
        periodView.setTime(curHour)
        clockTime.post {    clockTime.text  = "$curHour:$curMinute" }
    }

    class ClockViewHolder(private val clockView: ClockView){
        var curSecond = 0
        var curMinute = 0
        var curHour = 0
        private val clockTimer by lazy{
            Timer()
        }

        init{
            val calendar = Calendar.getInstance()
            curSecond = calendar.get(Calendar.SECOND)
            curMinute = calendar.get(Calendar.MINUTE)
            curHour = calendar.get(Calendar.HOUR)
            clockView.setTime(curSecond, curMinute, curHour)
        }

        fun startTimer(){
            clockTimer.schedule(object: TimerTask(){
                override fun run() {
                    curSecond = (curSecond + 1) % 60
                    if(curSecond == 0){
                        curMinute = (curMinute + 1) % 60
                        if(curMinute == 0){
                            curHour = (curHour + 1) % 60
                        }
                    }
                    clockView.setTime(curSecond, curMinute, curHour)
                }
            },0,1000)
        }

        fun stopTimer(){
            clockTimer.cancel()
        }
    }

}

