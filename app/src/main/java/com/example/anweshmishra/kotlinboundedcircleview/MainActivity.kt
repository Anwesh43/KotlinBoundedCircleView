package com.example.anweshmishra.kotlinboundedcircleview

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.boundedcircleview.BoundedCircleView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        BoundedCircleView.create(this, 0f, 0f, 250, 250)

        BoundedCircleView.create(this, 300f, 0f, 250, 250)

        BoundedCircleView.create(this, 600f, 0f, 250, 250)

        BoundedCircleView.create(this, 0f, 300f, 250, 250)

        BoundedCircleView.create(this, 300f, 300f, 250, 250)

        BoundedCircleView.create(this, 600f, 300f, 250, 250)


        BoundedCircleView.create(this, 0f, 600f, 250, 250)

        BoundedCircleView.create(this, 300f, 600f, 250, 250)

        BoundedCircleView.create(this, 600f, 600f, 250, 250)

        BoundedCircleView.create(this, 0f, 900f, 250, 250)

        BoundedCircleView.create(this, 300f, 900f, 250, 250)

        BoundedCircleView.create(this, 600f, 900f, 250, 250)

        BoundedCircleView.create(this, 0f, 1200f, 250, 250)

    }
}
