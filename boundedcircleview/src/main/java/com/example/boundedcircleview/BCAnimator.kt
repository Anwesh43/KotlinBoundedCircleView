package com.example.boundedcircleview

import android.view.View
import java.util.concurrent.ConcurrentLinkedQueue

/**
 * Created by anweshmishra on 12/05/18.
 */

class BCAnimator {

    val runner : BCRunner = BCRunner(0)

    var t : Thread ?= null

    fun start(view : View) {
        runner.start(view, {
            t = Thread(runner)
            t?.start()
        })
    }

    fun stop(view : View) {
        runner.stop(view)
    }

    fun pause() {
        if (runner.pause()) {
            while(true) {
                try {
                    t?.join()
                    break
                }
                catch (ex : Exception) {

                }
            }
        }
    }

    fun resume() {
        runner.resume {
            t = Thread(runner)
            t?.start()
        }
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
    companion object {
        val BCANIMATOR : BCAnimator = BCAnimator()
        fun getInstance() : BCAnimator = BCANIMATOR

    }
}