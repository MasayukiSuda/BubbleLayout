package com.daasuu.bl;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

/**
 * Created by sudamasayuki on 16/04/04.
 */
public class BubbleLayout extends FrameLayout {

    public static float DEFAULT_STROKE_WIDTH = -1;

    public static final int ARROW_DIRECTION_LEFT = 0;
    public static final int ARROW_DIRECTION_RIGHT = 1;
    public static final int ARROW_DIRECTION_TOP = 2;
    public static final int ARROW_DIRECTION_BOTTOM = 3;

    @IntDef(value = {
            ARROW_DIRECTION_LEFT, ARROW_DIRECTION_RIGHT, ARROW_DIRECTION_TOP, ARROW_DIRECTION_BOTTOM
    })
    public @interface ArrowDirection {
    }

    @ArrowDirection
    private int mArrowDirection;
    private Bubble mBubble;

    private float mArrowWidth;
    private float mCornersRadius;
    private float mArrowHeight;
    private float mArrowPosition;
    private int mBubbleColor;
    private float mStrokeWidth;
    private int mStrokeColor;

    public BubbleLayout(Context context) {
        this(context, null, 0);
    }

    public BubbleLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BubbleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.BubbleLayout);
        mArrowWidth = a.getDimension(R.styleable.BubbleLayout_bl_arrowWidth,
                convertDpToPixel(8, context));
        mArrowHeight = a.getDimension(R.styleable.BubbleLayout_bl_arrowHeight,
                convertDpToPixel(8, context));
        mCornersRadius = a.getDimension(R.styleable.BubbleLayout_bl_cornersRadius, 0);
        mArrowPosition = a.getDimension(R.styleable.BubbleLayout_bl_arrowPosition,
                convertDpToPixel(12, context));
        mBubbleColor = a.getColor(R.styleable.BubbleLayout_bl_bubbleColor, Color.WHITE);

        mStrokeWidth =
                a.getDimension(R.styleable.BubbleLayout_bl_strokeWidth, DEFAULT_STROKE_WIDTH);
        mStrokeColor = a.getColor(R.styleable.BubbleLayout_bl_strokeColor, Color.GRAY);

        int location = a.getInt(R.styleable.BubbleLayout_bl_arrowDirection, ARROW_DIRECTION_LEFT);
        switch (location) {
            case ARROW_DIRECTION_RIGHT:
                mArrowDirection = ARROW_DIRECTION_RIGHT;
                break;
            case ARROW_DIRECTION_TOP:
                mArrowDirection = ARROW_DIRECTION_TOP;
                break;
            case ARROW_DIRECTION_BOTTOM:
                mArrowDirection = ARROW_DIRECTION_BOTTOM;
                break;
            case ARROW_DIRECTION_LEFT:
            default:
                mArrowDirection = ARROW_DIRECTION_LEFT;
                break;
        }

        a.recycle();
        initPadding();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        initDrawable(0, getWidth(), 0, getHeight());
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (mBubble != null) mBubble.draw(canvas);
        super.dispatchDraw(canvas);
    }

    public void setArrowDirection(@ArrowDirection int arrowDirection) {
        mArrowDirection = arrowDirection;
    }

    public void setArrowWidth(float arrowWidth) {
        mArrowWidth = arrowWidth;
    }

    public void setCornersRadius(float cornersRadius) {
        mCornersRadius = cornersRadius;
    }

    public void setArrowHeight(float arrowHeight) {
        mArrowHeight = arrowHeight;
    }

    public void setArrowPosition(float arrowPosition) {
        mArrowPosition = arrowPosition;
    }

    public void setBubbleColor(int bubbleColor) {
        mBubbleColor = bubbleColor;
    }

    public void setStrokeWidth(float strokeWidth) {
        mStrokeWidth = strokeWidth;
    }

    public void setStrokeColor(int strokeColor) {
        mStrokeColor = strokeColor;
    }

    private void initDrawable(int left, int right, int top, int bottom) {
        if (right < left || bottom < top) return;

        RectF rectF = new RectF(left, top, right, bottom);
        mBubble = new Bubble(rectF, mArrowWidth, mCornersRadius, mArrowHeight, mArrowPosition,
                mStrokeWidth, mStrokeColor, mBubbleColor, mArrowDirection);
    }

    private void initPadding() {
        int paddingLeft = getPaddingLeft();
        int paddingRight = getPaddingRight();
        int paddingTop = getPaddingTop();
        int paddingBottom = getPaddingBottom();
        switch (mArrowDirection) {
            case ARROW_DIRECTION_LEFT:
                paddingLeft += mArrowWidth;
                break;
            case ARROW_DIRECTION_RIGHT:
                paddingRight += mArrowWidth;
                break;
            case ARROW_DIRECTION_TOP:
                paddingTop += mArrowHeight;
                break;
            case ARROW_DIRECTION_BOTTOM:
                paddingBottom += mArrowHeight;
                break;
        }
        if (mStrokeWidth > 0) {
            paddingLeft += mStrokeWidth;
            paddingRight += mStrokeWidth;
            paddingTop += mStrokeWidth;
            paddingBottom += mStrokeWidth;
        }
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
    }

    static float convertDpToPixel(float dp, Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
