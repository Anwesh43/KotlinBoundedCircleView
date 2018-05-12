package com.example.boundedcircleview

/**
 * Created by anweshmishra on 12/05/18.
 */

import android.content.Context
import android.graphics.*
import android.view.View
import android.view.MotionEvent

class BoundedCircleView (ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}