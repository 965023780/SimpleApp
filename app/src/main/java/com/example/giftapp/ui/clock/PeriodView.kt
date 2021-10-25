package com.example.giftapp.ui.clock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.core.content.ContextCompat
import com.example.giftapp.R
import com.example.giftapp.util.DisplayUtils

/**
 * creator: sothoth
 * date: 2021/10/25
 * description: 钟表时段
 */
class PeriodView : DiskView {


    enum class PeriodTime {
        AM,
        PM
    }

    private var curPeriod = PeriodTime.AM

    init {
        radius = DisplayUtils.getScreenWidth(context) * 0.5f * 2f / 7f
        paint.style = Paint.Style.FILL
        paint.textSize = DisplayUtils.dp2px(context, 30f).toFloat()
        cx = DisplayUtils.getScreenWidth(context) / 2f
        cy = DisplayUtils.getScreenWidth(context) * 0.5f * 3f / 7f
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    override fun drawDisk(canvas: Canvas?) {
        drawCircle(canvas)
        drawPeriod(canvas)
    }

    override fun drawCircle(canvas: Canvas?) {
        paint.color = ContextCompat.getColor(context, R.color.clockPeriod)
        canvas?.drawCircle(cx, cy, radius, paint)
    }

    private fun drawPeriod(canvas: Canvas?) {
        val bounds = Rect()
        paint.color = ContextCompat.getColor(context, R.color.clockText)
        when (curPeriod) {
            PeriodTime.AM -> {
                paint.getTextBounds("AM", 0, 2, bounds)
                canvas?.drawText("AM", cx - bounds.width() / 2, cy + bounds.height() / 2, paint)
            }
            PeriodTime.PM -> {
                paint.getTextBounds("PM", 0, 2, bounds)
                canvas?.drawText("PM", cx - bounds.width() / 2, cy + bounds.height() / 2, paint)
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean =
        computeDistFromCenter(event?.x!!, event.y) <= radius


}