package com.example.boundedcircleview

import android.view.View
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Created by anweshmishra on 12/05/18.
 */

class BCAnimator {

    fun start(view : View) {

    }

    fun stop(view : View) {

    }

    data class BCRunner(var i : Int) : Runnable {

        var running = false

        val views : ConcurrentLinkedQueue<View> = ConcurrentLinkedQueue()

        override fun run() {
            while(running) {
                try {
                    Thread.sleep(50)
                    views.forEach {
                        it.postInvalidate()
                    }
                }
                catch (ex : Exception) {

                }
            }
        }

        fun start(view : View,cb : () -> Unit) {
            views.add(view)
            if (views.size == 1) {
                running = true
                cb()
            }
        }

        fun stop(view : View) {
            if (views.contains(view)) {
                views.remove(view)
                if (views.size == 0) {
                    running = false
                }
            }
        }

        fun pause() : Boolean {
            if (running) {
                running = false
                return true
            }
            return false
        }

        fun resume(cb : () -> Unit) {
            if (!running && views.size > 0) {
                running = true
                cb()
            }
        }
    }
}