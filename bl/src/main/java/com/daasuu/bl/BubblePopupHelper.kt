package com.daasuu.bl

import android.content.Context
import androidx.core.content.ContextCompat
import android.view.ViewGroup
import android.widget.PopupWindow

/**
 * Created by sudamasayuki on 16/05/02.
 */
object BubblePopupHelper {

    fun create(context: Context, bubbleLayout: BubbleLayout): PopupWindow {

        val popupWindow = PopupWindow(context)

        popupWindow.contentView = bubbleLayout
        popupWindow.isOutsideTouchable = true
        popupWindow.width = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow.height = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow.animationStyle = android.R.style.Animation_Dialog
        // change background color to transparent
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.popup_window_transparent))

        return popupWindow
    }

}
