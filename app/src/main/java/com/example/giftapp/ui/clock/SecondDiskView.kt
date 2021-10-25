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
 * description: 钟表秒盘
 */
class SecondDiskView : DiskView {
    private val bigPointRadius by lazy {
        DisplayUtils.dp2px(context, 20f).toFloat() / 4
    }

    private val smallPointRadius by lazy {
        DisplayUtils.dp2px(context, 20f).toFloat() / 6
    }

    init {
        radius = DisplayUtils.getScreenWidth(context) * 0.65f
        paint.style = Paint.Style.FILL
        cx = DisplayUtils.getScreenWidth(context) / 2f
        cy = DisplayUtils.getScreenWidth(context) * 0.5f * 3f / 7f
    }

    constructor(context: Context) : super(context)

    constructor(context: Context, attr: AttributeSet) : super(context, attr)

    override fun drawDisk(canvas: Canvas?) {
        drawCircle(canvas)
    }

    override fun drawCircle(canvas: Canvas?) {
        val pointX = radius + cx + DisplayUtils.dp2px(context, 20f)
        val pointY = cy
        val degree = curDegree.toInt() / 6
        paint.color = ContextCompat.getColor(context, R.color.clockSecond)
        val draw = { i: Int ->
            when (i % 5) {
                0 -> canvas?.drawCircle(pointX, pointY, bigPointRadius, paint)
                else -> canvas?.drawCircle(pointX, pointY, smallPointRadius, paint)
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