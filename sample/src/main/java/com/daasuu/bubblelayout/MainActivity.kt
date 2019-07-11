package com.daasuu.bubblelayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.PopupWindow

import com.daasuu.bl.ArrowDirection
import com.daasuu.bl.BubbleLayout
import com.daasuu.bl.BubblePopupHelper

import java.util.Random

class MainActivity : AppCompatActivity() {

    private var popupWindow: PopupWindow? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<View>(R.id.btn_popup) as Button

        val bubbleLayout = LayoutInflater.from(this).inflate(R.layout.layout_sample_popup, null) as BubbleLayout
        popupWindow = BubblePopupHelper.create(this, bubbleLayout)
        val random = Random()

        button.setOnClickListener { v ->
            val location = IntArray(2)
            v.getLocationInWindow(location)
            if (random.nextBoolean()) {
                bubbleLayout.setArrowDirection(ArrowDirection.TOP)
            } else {
                bubbleLayout.setArrowDirection(ArrowDirection.BOTTOM)
            }
            popupWindow!!.showAtLocation(v, Gravity.NO_GRAVITY, location[0], v.height + location[1])
        }

    }

}
