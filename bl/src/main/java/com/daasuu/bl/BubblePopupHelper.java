package com.daasuu.bl;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import android.view.ViewGroup;
import android.widget.PopupWindow;

/**
 * Created by sudamasayuki on 16/05/02.
 */
public class BubblePopupHelper {

    public static PopupWindow create(@NonNull Context context, @NonNull BubbleLayout bubbleLayout) {

        PopupWindow popupWindow = new PopupWindow(context);

        popupWindow.setContentView(bubbleLayout);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
        // change background color to transparent
        popupWindow.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.popup_window_transparent));

        return popupWindow;
    }

}
