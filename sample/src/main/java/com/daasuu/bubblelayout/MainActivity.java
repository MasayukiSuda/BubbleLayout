package com.daasuu.bubblelayout;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;

import com.daasuu.bl.ArrowDirection;
import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;

import java.util.Random;

import static com.daasuu.bl.ArrowDirection.*;

public class MainActivity extends AppCompatActivity {

    private PopupWindow popupWindow;
    private ArrowDirection[] randomArrowDirections = {
        TOP,
        BOTTOM,
        TOP_RIGHT,
        BOTTOM_RIGHT
    };

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
                ArrowDirection direction = randomArrowDirections[random.nextInt(randomArrowDirections.length)];
                switch (direction) {
                    case TOP_RIGHT:
                    case BOTTOM_RIGHT:
                        xoff = v.getWidth() - bubbleWidth;
                        break;
                    case TOP:
                    case BOTTOM:
                }
                bubbleLayout.setArrowDirection(direction);
                bubbleLayout.setArrowPosition(v.getWidth() / 2f);
                popupWindow.showAsDropDown(v, xoff, yoff);
            }
        });

    }

}
