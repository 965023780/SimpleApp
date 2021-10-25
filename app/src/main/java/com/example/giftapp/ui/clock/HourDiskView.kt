package com.example.giftapp.ui.clock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.giftapp.R
import com.example.giftapp.util.DisplayUtils

/**
 * creator: sothoth
 * date: 2021/10/17
 * description: 钟表时表盘
 */

class HourDiskView : DiskView {
    init {
        radius = DisplayUtils.getScreenWidth(context) * 0.45f
        paint.style = Paint.Style.FILL
        cx = DisplayUtils.getScreenWidth(context) / 2f
        cy = DisplayUtils.getScreenWidth(context) * 0.5f * 3f / 7f
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    override fun drawDisk(canvas: Canvas?) {
        drawCircle(canvas)
        drawNumber(canvas)
    }

    override fun drawCircle(canvas: Canvas?) {
        paint.color = ContextCompat.getColor(context, R.color.clockHour)
        canvas?.drawCircle(cx, cy, radius, paint)
    }

    private fun drawNumber(canvas: Canvas?) {
        val bounds = Rect()
        val degree = (curDegree + 0.5f).toInt() / 6
        paint.textSize = DisplayUtils.dp2px(context, 30f).toFloat()
        paint.getTextBounds("0", 0, "0".length, bounds)
        paint.color = ContextCompat.getColor(context, R.color.clockText)
        val draw = { i: Int ->
            if (i % 5 == 0) {
                paint.getTextBounds(i.toString(), 0, i.toString().length, bounds)
                canvas?.drawText(
                    (i / 5).toString(),
                    cx - bounds.width() / 2,
                    cy + radius - bounds.height() / 2,
                    paint
                )
            }
            canvas?.rotate(-6f, cx, cy)
        }
        for (i in degree until 60) {
            draw(i)
        }
        for (i in 0 until degree) {
            draw(i)
        }
    }

}


