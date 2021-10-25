package com.example.giftapp.util

import android.content.Context
import android.util.DisplayMetrics

/**
 * creator: sothoth
 * date: 2021/10/17
 * description: 屏幕类工具
 */
class DisplayUtils {
    companion object{
        fun getScreenHeight(context: Context): Int{
            val displayMetrics = context.resources.displayMetrics
            return displayMetrics.heightPixels
        }

        fun getScreenWidth(context: Context): Int{
            val displayMetrics = context.resources.displayMetrics
            return displayMetrics.widthPixels
        }

        fun dp2px(context: Context, dpValue: Float):Int{
            return (dpValue * context.resources.displayMetrics.density + 0.5f).toInt()
        }

        fun px2dp(context: Context,pxValue: Float): Int{
            return (pxValue / context.resources.displayMetrics.density + 0.5f).toInt()
        }

    }
}