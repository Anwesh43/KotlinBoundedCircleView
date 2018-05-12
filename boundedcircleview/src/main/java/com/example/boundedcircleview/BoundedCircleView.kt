package com.example.boundedcircleview

/**
 * Created by anweshmishra on 12/05/18.
 */

import android.app.Activity
import android.content.Context
import android.graphics.*
import android.view.View
import android.view.MotionEvent

class BoundedCircleView (ctx : Context) : View(ctx) {

    private val paint : Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val renderer : Renderer = Renderer(this)

    override fun onDraw(canvas : Canvas) {
        renderer.render(canvas, paint)
    }

    fun update() {
        postInvalidate()
    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }

    data class State(var prevScale : Float = 0f, var dir : Float = 0f, var j : Int = 0) {

        val scales : Array<Float> = arrayOf(0f, 0f, 0f)

        fun update(stopcb : (Float) -> Unit) {
            scales[j] += dir * 0.1f
            if (Math.abs(scales[j] - prevScale) > 1) {
                scales[j] = prevScale + dir
                j += dir.toInt()
                if (j == scales.size || j == -1) {
                    j -= dir.toInt()
                    dir = 0f
                    prevScale = scales[j]
                    stopcb(prevScale)
                }
            }
        }

        fun startUpdating(startcb : () -> Unit) {
            if (dir == 0f) {
                dir = 1 - 2 * prevScale
                startcb()
            }
        }
    }

    data class BoundedCircle(var i : Int, val state : State = State()) {

        fun draw(canvas : Canvas, paint : Paint) {
            val w : Float = canvas.width.toFloat()
            val h : Float = canvas.height.toFloat()
            val l : Float = (Math.min(w, h) / 3) * state.scales[1]
            val r : Float = Math.min(w,h)/10
            val deg : Float = 360f * state.scales[0]
            val pointAt135 : PointF = PointF(l + r * Math.cos(3 * Math.PI/4).toFloat(), l + r * Math.sin(3 * Math.PI/4).toFloat())
            val pointAt45 : PointF = PointF( r * Math.cos (-Math.PI/4).toFloat(), l + r * Math.sin(-Math.PI/4).toFloat())
            val drawArc : () -> Unit = {
                canvas.drawArc(RectF(-r, -r, r, r), 0f, deg, false, paint)
            }
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = Math.min(w, h) / 50
            paint.strokeCap = Paint.Cap.ROUND
            canvas.save()
            canvas.translate(w/2, h/2)
            drawArc()
            for (i in 0..3) {
                canvas.save()
                canvas.rotate(90f * i)
                canvas.drawLine(r, 0f, l -r, 0f, paint)
                canvas.save()
                canvas.translate(l, 0f)
                drawArc()
                canvas.restore()
                if (state.j == 2) {
                    canvas.drawPointLine(pointAt135, pointAt45, state.scales[2], paint)
                }
                canvas.restore()
            }
            canvas.restore()
        }

        fun update(stopcb : (Float) -> Unit) {
            state.update(stopcb)
        }

        fun startUpdating(startcb : () -> Unit) {
            state.startUpdating(startcb)
        }
    }

    data class Renderer(var view : BoundedCircleView) {

        private val boundedCircle : BoundedCircle = BoundedCircle(0)

        fun render(canvas : Canvas, paint : Paint) {
            canvas.drawColor(Color.parseColor("#212121"))
            boundedCircle.draw(canvas, paint)
            boundedCircle.update {
                BCAnimator.getInstance().stop(view)
            }
        }

        fun handleTap() {
            boundedCircle.startUpdating {
                BCAnimator.getInstance().start(view)
            }
        }
    }

    companion object {

        fun create(activity : Activity) : BoundedCircleView {
            val view : BoundedCircleView = BoundedCircleView(activity)
            activity.setContentView(view)
            return view
        }

        fun pause() {
            BCAnimator.getInstance().pause()
        }

        fun resume() {
            BCAnimator.getInstance().resume()
        }

    }
}

fun Canvas.drawPointLine(point1 : PointF, point2 : PointF, scale : Float, paint : Paint) {
    drawLine(point1.x, point1.y, point1.x + (point2.x - point1.x) * scale, (point1.y) + (point2.y - point1.y) * scale, paint)
}