package com.example.giftapp.ui.clock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import androidx.core.content.ContextCompat
import com.example.giftapp.R
import com.example.giftapp.util.DisplayUtils

/**
 * creator: sothoth
 * date: 2021/10/17
 * description: 闹分盘
 */
class MinuteDiskView : DiskView {

    init {
        radius = DisplayUtils.getScreenWidth(context) * 0.65f
        paint.style = Paint.Style.FILL
        cx = DisplayUtils.getScreenWidth(context) / 2f
        cy = DisplayUtils.getScreenWidth(context) * 0.5f * 3f / 7f
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    override fun setTime(time: Int) {
        val newDegree =time / 60f * 360
        if(curDegree != newDegree){
            curDegree = newDegree
            postInvalidate()
        }
    }

    override fun drawDisk(canvas: Canvas?) {
        drawCircle(canvas)
        drawNumber(canvas)
    }

    override fun drawCircle(canvas: Canvas?) {
        paint.color = ContextCompat.getColor(context, R.color.clockMinute)
        canvas?.drawCircle(cx, cy, radius, paint)
    }

    private fun drawNumber(canvas: Canvas?) {
        val bounds = Rect()
        val pointX = cx
        val pointY = cy + radius
        val pointRadius = DisplayUtils.dp2px(context, 20f).toFloat() / 6
        val degree = (curDegree + 0.5f).toInt() / 6
        paint.textSize = DisplayUtils.dp2px(context, 20f).toFloat()
        paint.getTextBounds("0", 0, "0".length, bounds)
        paint.color = ContextCompat.getColor(context, R.color.clockText)
        var textHeight = bounds.height()
        val draw = { i: Int ->
            when (i % 5) {
                0 -> {
                    paint.getTextBounds(i.toString(), 0, i.toString().length, bounds)
                    textHeight = bounds.height()
                    canvas?.drawText(
                        i.toString(),
                        pointX - bounds.width() * 2 / 3,
                        pointY - textHeight,
                        paint
                    )
                }
                else -> canvas?.drawCircle(pointX, pointY - textHeight * 3 / 2, pointRadius, paint)
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