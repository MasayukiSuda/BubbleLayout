package com.daasuu.bubblelayout;

import android.content.Context;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import com.daasuu.bl.ArrowDirection;
import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button) findViewById(R.id.btn_popup);

        final BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(this).inflate(R.layout.layout_sample_popup, null);
        bubbleLayout.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        final int bubbleWidth = bubbleLayout.getMeasuredWidth();

        popupWindow = BubblePopupHelper.create(this, bubbleLayout);
        final Random random = new Random();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int xoff = 0;
                int yoff = 0;
                if (random.nextBoolean()) {
                    bubbleLayout.setArrowDirection(ArrowDirection.TOP);
                } else {
                    bubbleLayout.setArrowDirection(ArrowDirection.TOP_RIGHT);
                    xoff = v.getWidth() - bubbleWidth;
                }
                bubbleLayout.setArrowPosition(v.getWidth() / 2f);
                popupWindow.showAsDropDown(v, xoff, yoff);
            }
        });

    }

}
