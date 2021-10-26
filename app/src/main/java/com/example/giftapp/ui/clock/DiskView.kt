package com.example.giftapp.ui.clock

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.PI
import kotlin.math.acos
import kotlin.math.sqrt

/**
 * creator: sothoth
 * date: 2021/10/17
 * description: 表盘基类
 */
abstract class DiskView: View{

    protected var radius = 0f
    protected val paint by lazy{
        Paint()
    }
    protected var cx = 0f
    protected var cy = 0f
    protected var curDegree = 0f
    private var rotateDegree = 0f
    private var startDegree = 0f
    private val curPoint by lazy{
        Array(2) {0f}
    }

    constructor(context: Context): super(context)

    constructor(context: Context,attr: AttributeSet):super(context,attr)

    protected abstract fun drawDisk(canvas: Canvas?)
    protected abstract fun drawCircle(canvas: Canvas?)
    abstract fun setTime(time: Int)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawDisk(canvas)
    }


    override fun onTouchEvent(event: MotionEvent?): Boolean {
        curPoint[0] = event?.x!!
        curPoint[1] = event.y
        when(event.action){
            MotionEvent.ACTION_DOWN -> {
                val dist = computeDistFromCenter(curPoint[0], curPoint[1])
                if(dist  <= radius){
                    startDegree = computeCurrentAngle(curPoint[0], curPoint[1])
                }else{
                    startDegree = 0f
                    return false
                }
            }
            MotionEvent.ACTION_MOVE -> {
                val dist = computeDistFromCenter(curPoint[0], curPoint[1])
                if(dist  <= radius){
                    rotateDegree = computeCurrentAngle(curPoint[0], curPoint[1]) - startDegree
                    startDegree += rotateDegree
                    curDegree = (curDegree + rotateDegree + 360) % 360
                    postInvalidate()
                }else{
                    return false
                }
            }

        }
        return true
    }

    protected fun computeDistFromCenter(x: Float, y: Float): Float =
        sqrt( ((x - cx) * (x - cx) + (y - cy) * (y - cy)).toDouble()).toFloat()

    protected fun computeCurrentAngle(x: Float, y: Float): Float{
        val dist = computeDistFromCenter(x, y)
        return if(y < cy){
            (-180 * acos((x - cx) / dist.toDouble()) / PI).toFloat()
        }else{
            (180 * acos((x - cx) / dist.toDouble()) / PI).toFloat()
        }
    }

}