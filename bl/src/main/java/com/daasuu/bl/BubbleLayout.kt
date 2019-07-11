package com.daasuu.bl

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.widget.FrameLayout

/**
 * Bubble View for Android with custom stroke width and color, arrow size, position and direction.
 * Created by sudamasayuki on 16/04/04.
 */
class BubbleLayout @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : FrameLayout(context, attrs, defStyleAttr) {

    private var mArrowDirection: ArrowDirection? = null
    private var mBubble: Bubble? = null

    private var mArrowWidth: Float = 0.toFloat()
    private var mCornersRadius: Float = 0.toFloat()
    private var mArrowHeight: Float = 0.toFloat()
    private var mArrowPosition: Float = 0.toFloat()
    private var mBubbleColor: Int = 0
    private var mStrokeWidth: Float = 0.toFloat()
    private var mStrokeColor: Int = 0

    init {

        val a = getContext().obtainStyledAttributes(attrs, R.styleable.BubbleLayout)
        mArrowWidth = a.getDimension(R.styleable.BubbleLayout_bl_arrowWidth,
                convertDpToPixel(8f, context))
        mArrowHeight = a.getDimension(R.styleable.BubbleLayout_bl_arrowHeight,
                convertDpToPixel(8f, context))
        mCornersRadius = a.getDimension(R.styleable.BubbleLayout_bl_cornersRadius, 0f)
        mArrowPosition = a.getDimension(R.styleable.BubbleLayout_bl_arrowPosition,
                convertDpToPixel(12f, context))
        mBubbleColor = a.getColor(R.styleable.BubbleLayout_bl_bubbleColor, Color.WHITE)

        mStrokeWidth = a.getDimension(R.styleable.BubbleLayout_bl_strokeWidth, DEFAULT_STROKE_WIDTH)
        mStrokeColor = a.getColor(R.styleable.BubbleLayout_bl_strokeColor, Color.GRAY)

        val location = a.getInt(R.styleable.BubbleLayout_bl_arrowDirection, ArrowDirection.LEFT.value)
        mArrowDirection = ArrowDirection.fromInt(location)

        a.recycle()
        initPadding()
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        initDrawable(0, width, 0, height)
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (mBubble != null) mBubble!!.draw(canvas)
        super.dispatchDraw(canvas)
    }

    private fun initDrawable(left: Int, right: Int, top: Int, bottom: Int) {
        if (right < left || bottom < top) return

        val rectF = RectF(left.toFloat(), top.toFloat(), right.toFloat(), bottom.toFloat())
        when (mArrowDirection) {
            ArrowDirection.LEFT_CENTER, ArrowDirection.RIGHT_CENTER -> mArrowPosition = (bottom - top) / 2 - mArrowHeight / 2
            ArrowDirection.TOP_CENTER, ArrowDirection.BOTTOM_CENTER -> mArrowPosition = (right - left) / 2 - mArrowWidth / 2
            else -> {
            }
        }
        mBubble = mArrowDirection?.let {
            Bubble(rectF, mArrowWidth, mCornersRadius, mArrowHeight, mArrowPosition,
                mStrokeWidth, mStrokeColor, mBubbleColor, it)
        }
    }

    private fun initPadding() {
        var paddingLeft = paddingLeft
        var paddingRight = paddingRight
        var paddingTop = paddingTop
        var paddingBottom = paddingBottom
        when (mArrowDirection) {
            ArrowDirection.LEFT, ArrowDirection.LEFT_CENTER -> paddingLeft += mArrowWidth.toInt()
            ArrowDirection.RIGHT, ArrowDirection.RIGHT_CENTER -> paddingRight += mArrowWidth.toInt()
            ArrowDirection.TOP, ArrowDirection.TOP_CENTER -> paddingTop += mArrowHeight.toInt()
            ArrowDirection.BOTTOM, ArrowDirection.BOTTOM_CENTER -> paddingBottom += mArrowHeight.toInt()
        }
        if (mStrokeWidth > 0) {
            paddingLeft += mStrokeWidth.toInt()
            paddingRight += mStrokeWidth.toInt()
            paddingTop += mStrokeWidth.toInt()
            paddingBottom += mStrokeWidth.toInt()
        }
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    private fun resetPadding() {
        var paddingLeft = paddingLeft
        var paddingRight = paddingRight
        var paddingTop = paddingTop
        var paddingBottom = paddingBottom
        when (mArrowDirection) {
            ArrowDirection.LEFT, ArrowDirection.LEFT_CENTER -> paddingLeft -= mArrowWidth.toInt()
            ArrowDirection.RIGHT, ArrowDirection.RIGHT_CENTER -> paddingRight -= mArrowWidth.toInt()
            ArrowDirection.TOP, ArrowDirection.TOP_CENTER -> paddingTop -= mArrowHeight.toInt()
            ArrowDirection.BOTTOM, ArrowDirection.BOTTOM_CENTER -> paddingBottom -= mArrowHeight.toInt()
        }
        if (mStrokeWidth > 0) {
            paddingLeft -= mStrokeWidth.toInt()
            paddingRight -= mStrokeWidth.toInt()
            paddingTop -= mStrokeWidth.toInt()
            paddingBottom -= mStrokeWidth.toInt()
        }
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom)
    }

    fun setArrowDirection(arrowDirection: ArrowDirection): BubbleLayout {
        resetPadding()
        mArrowDirection = arrowDirection
        initPadding()
        return this
    }

    fun setArrowWidth(arrowWidth: Float): BubbleLayout {
        resetPadding()
        mArrowWidth = arrowWidth
        initPadding()
        return this
    }

    fun setCornersRadius(cornersRadius: Float): BubbleLayout {
        mCornersRadius = cornersRadius
        requestLayout()
        return this
    }

    fun setArrowHeight(arrowHeight: Float): BubbleLayout {
        resetPadding()
        mArrowHeight = arrowHeight
        initPadding()
        return this
    }

    fun setArrowPosition(arrowPosition: Float): BubbleLayout {
        resetPadding()
        mArrowPosition = arrowPosition
        initPadding()
        return this
    }

    fun setBubbleColor(bubbleColor: Int): BubbleLayout {
        mBubbleColor = bubbleColor
        requestLayout()
        return this
    }

    fun setStrokeWidth(strokeWidth: Float): BubbleLayout {
        resetPadding()
        mStrokeWidth = strokeWidth
        initPadding()
        return this
    }

    fun setStrokeColor(strokeColor: Int): BubbleLayout {
        mStrokeColor = strokeColor
        requestLayout()
        return this
    }

    fun getArrowDirection(): ArrowDirection? {
        return mArrowDirection
    }

    fun getArrowWidth(): Float {
        return mArrowWidth
    }

    fun getCornersRadius(): Float {
        return mCornersRadius
    }

    fun getArrowHeight(): Float {
        return mArrowHeight
    }

    fun getArrowPosition(): Float {
        return mArrowPosition
    }

    fun getBubbleColor(): Int {
        return mBubbleColor
    }

    fun getStrokeWidth(): Float {
        return mStrokeWidth
    }

    fun getStrokeColor(): Int {
        return mStrokeColor
    }

    companion object {

        var DEFAULT_STROKE_WIDTH = -1f

        internal fun convertDpToPixel(dp: Float, context: Context): Float {
            val metrics = context.resources.displayMetrics
            return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT)
        }
    }
}
