package com.daasuu.bl

import android.graphics.Canvas
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable

/**
 * Created by sudamasayuki on 16/03/27.
 */
internal class Bubble(private val mRect: RectF, private val mArrowWidth: Float, private val mCornersRadius: Float, private val mArrowHeight: Float, private val mArrowPosition: Float, private val mStrokeWidth: Float, strokeColor: Int, bubbleColor: Int, arrowDirection: ArrowDirection) : Drawable() {
    private val mPath = Path()
    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var mStrokePath: Path? = null
    private var mStrokePaint: Paint? = null

    init {

        mPaint.color = bubbleColor

        if (mStrokeWidth > 0) {
            mStrokePaint = Paint(Paint.ANTI_ALIAS_FLAG)
            mStrokePaint!!.color = strokeColor
            mStrokePath = Path()

            initPath(arrowDirection, mPath, mStrokeWidth)
            initPath(arrowDirection, mStrokePath!!, 0f)
        } else {
            initPath(arrowDirection, mPath, 0f)
        }
    }

    override fun onBoundsChange(bounds: Rect) {
        super.onBoundsChange(bounds)
    }

    override fun draw(canvas: Canvas) {
        if (mStrokeWidth > 0) {
            canvas.drawPath(mStrokePath!!, mStrokePaint!!)
        }
        canvas.drawPath(mPath, mPaint)
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun setAlpha(alpha: Int) {
        mPaint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        mPaint.colorFilter = cf
    }

    override fun getIntrinsicWidth(): Int {
        return mRect.width().toInt()
    }

    override fun getIntrinsicHeight(): Int {
        return mRect.height().toInt()
    }

    private fun initPath(arrowDirection: ArrowDirection, path: Path, strokeWidth: Float) {
        when (arrowDirection) {
            ArrowDirection.LEFT, ArrowDirection.LEFT_CENTER -> {
                if (mCornersRadius <= 0) {
                    initLeftSquarePath(mRect, path, strokeWidth)

                }

                if (strokeWidth > 0 && strokeWidth > mCornersRadius) {
                    initLeftSquarePath(mRect, path, strokeWidth)

                }

                initLeftRoundedPath(mRect, path, strokeWidth)
            }
            ArrowDirection.TOP, ArrowDirection.TOP_CENTER -> {
                if (mCornersRadius <= 0) {
                    initTopSquarePath(mRect, path, strokeWidth)

                }

                if (strokeWidth > 0 && strokeWidth > mCornersRadius) {
                    initTopSquarePath(mRect, path, strokeWidth)

                }

                initTopRoundedPath(mRect, path, strokeWidth)
            }
            ArrowDirection.RIGHT, ArrowDirection.RIGHT_CENTER -> {
                if (mCornersRadius <= 0) {
                    initRightSquarePath(mRect, path, strokeWidth)

                }

                if (strokeWidth > 0 && strokeWidth > mCornersRadius) {
                    initRightSquarePath(mRect, path, strokeWidth)

                }

                initRightRoundedPath(mRect, path, strokeWidth)
            }
            ArrowDirection.BOTTOM, ArrowDirection.BOTTOM_CENTER -> {
                if (mCornersRadius <= 0) {
                    initBottomSquarePath(mRect, path, strokeWidth)

                }

                if (strokeWidth > 0 && strokeWidth > mCornersRadius) {
                    initBottomSquarePath(mRect, path, strokeWidth)

                }

                initBottomRoundedPath(mRect, path, strokeWidth)
            }
        }
    }

    private fun initLeftRoundedPath(rect: RectF, path: Path, strokeWidth: Float) {

        path.moveTo(mArrowWidth + rect.left + mCornersRadius + strokeWidth, rect.top + strokeWidth)
        path.lineTo(rect.width() - mCornersRadius - strokeWidth, rect.top + strokeWidth)
        path.arcTo(RectF(rect.right - mCornersRadius, rect.top + strokeWidth, rect.right - strokeWidth,
                mCornersRadius + rect.top), 270f, 90f)

        path.lineTo(rect.right - strokeWidth, rect.bottom - mCornersRadius - strokeWidth)
        path.arcTo(RectF(rect.right - mCornersRadius, rect.bottom - mCornersRadius,
                rect.right - strokeWidth, rect.bottom - strokeWidth), 0f, 90f)


        path.lineTo(rect.left + mArrowWidth + mCornersRadius + strokeWidth, rect.bottom - strokeWidth)


        path.arcTo(RectF(rect.left + mArrowWidth + strokeWidth, rect.bottom - mCornersRadius,
                mCornersRadius + rect.left + mArrowWidth, rect.bottom - strokeWidth), 90f, 90f)

        path.lineTo(rect.left + mArrowWidth + strokeWidth, mArrowHeight + mArrowPosition - strokeWidth / 2)

        path.lineTo(rect.left + strokeWidth + strokeWidth, mArrowPosition + mArrowHeight / 2)


        path.lineTo(rect.left + mArrowWidth + strokeWidth, mArrowPosition + strokeWidth / 2)

        path.lineTo(rect.left + mArrowWidth + strokeWidth, rect.top + mCornersRadius + strokeWidth)

        path.arcTo(RectF(rect.left + mArrowWidth + strokeWidth, rect.top + strokeWidth, mCornersRadius
                + rect.left + mArrowWidth, mCornersRadius + rect.top), 180f, 90f)

        path.close()
    }

    private fun initLeftSquarePath(rect: RectF, path: Path, strokeWidth: Float) {

        path.moveTo(mArrowWidth + rect.left + strokeWidth, rect.top + strokeWidth)
        path.lineTo(rect.width() - strokeWidth, rect.top + strokeWidth)

        path.lineTo(rect.right - strokeWidth, rect.bottom - strokeWidth)

        path.lineTo(rect.left + mArrowWidth + strokeWidth, rect.bottom - strokeWidth)


        path.lineTo(rect.left + mArrowWidth + strokeWidth, mArrowHeight + mArrowPosition - strokeWidth / 2)
        path.lineTo(rect.left + strokeWidth + strokeWidth, mArrowPosition + mArrowHeight / 2)
        path.lineTo(rect.left + mArrowWidth + strokeWidth, mArrowPosition + strokeWidth / 2)

        path.lineTo(rect.left + mArrowWidth + strokeWidth, rect.top + strokeWidth)


        path.close()
    }


    private fun initTopRoundedPath(rect: RectF, path: Path, strokeWidth: Float) {
        path.moveTo(rect.left + Math.min(mArrowPosition, mCornersRadius) + strokeWidth, rect.top + mArrowHeight + strokeWidth)
        path.lineTo(rect.left + mArrowPosition + strokeWidth / 2, rect.top + mArrowHeight + strokeWidth)
        path.lineTo(rect.left + mArrowWidth / 2 + mArrowPosition, rect.top + strokeWidth + strokeWidth)
        path.lineTo(rect.left + mArrowWidth + mArrowPosition - strokeWidth / 2, rect.top + mArrowHeight + strokeWidth)
        path.lineTo(rect.right - mCornersRadius - strokeWidth, rect.top + mArrowHeight + strokeWidth)

        path.arcTo(RectF(rect.right - mCornersRadius,
                rect.top + mArrowHeight + strokeWidth, rect.right - strokeWidth, mCornersRadius + rect.top + mArrowHeight), 270f, 90f)
        path.lineTo(rect.right - strokeWidth, rect.bottom - mCornersRadius - strokeWidth)

        path.arcTo(RectF(rect.right - mCornersRadius, rect.bottom - mCornersRadius,
                rect.right - strokeWidth, rect.bottom - strokeWidth), 0f, 90f)
        path.lineTo(rect.left + mCornersRadius + strokeWidth, rect.bottom - strokeWidth)

        path.arcTo(RectF(rect.left + strokeWidth, rect.bottom - mCornersRadius,
                mCornersRadius + rect.left, rect.bottom - strokeWidth), 90f, 90f)

        path.lineTo(rect.left + strokeWidth, rect.top + mArrowHeight + mCornersRadius + strokeWidth)

        path.arcTo(RectF(rect.left + strokeWidth, rect.top + mArrowHeight + strokeWidth, mCornersRadius + rect.left, mCornersRadius + rect.top + mArrowHeight), 180f, 90f)

        path.close()
    }

    private fun initTopSquarePath(rect: RectF, path: Path, strokeWidth: Float) {
        path.moveTo(rect.left + mArrowPosition + strokeWidth, rect.top + mArrowHeight + strokeWidth)

        path.lineTo(rect.left + mArrowPosition + strokeWidth / 2, rect.top + mArrowHeight + strokeWidth)
        path.lineTo(rect.left + mArrowWidth / 2 + mArrowPosition, rect.top + strokeWidth + strokeWidth)
        path.lineTo(rect.left + mArrowWidth + mArrowPosition - strokeWidth / 2, rect.top + mArrowHeight + strokeWidth)
        path.lineTo(rect.right - strokeWidth, rect.top + mArrowHeight + strokeWidth)

        path.lineTo(rect.right - strokeWidth, rect.bottom - strokeWidth)

        path.lineTo(rect.left + strokeWidth, rect.bottom - strokeWidth)


        path.lineTo(rect.left + strokeWidth, rect.top + mArrowHeight + strokeWidth)

        path.lineTo(rect.left + mArrowPosition + strokeWidth, rect.top + mArrowHeight + strokeWidth)


        path.close()
    }


    private fun initRightRoundedPath(rect: RectF, path: Path, strokeWidth: Float) {

        path.moveTo(rect.left + mCornersRadius + strokeWidth, rect.top + strokeWidth)
        path.lineTo(rect.width() - mCornersRadius - mArrowWidth - strokeWidth, rect.top + strokeWidth)
        path.arcTo(RectF(rect.right - mCornersRadius - mArrowWidth,
                rect.top + strokeWidth, rect.right - mArrowWidth - strokeWidth, mCornersRadius + rect.top), 270f, 90f)

        path.lineTo(rect.right - mArrowWidth - strokeWidth, mArrowPosition + strokeWidth / 2)
        path.lineTo(rect.right - strokeWidth - strokeWidth, mArrowPosition + mArrowHeight / 2)
        path.lineTo(rect.right - mArrowWidth - strokeWidth, mArrowPosition + mArrowHeight - strokeWidth / 2)
        path.lineTo(rect.right - mArrowWidth - strokeWidth, rect.bottom - mCornersRadius - strokeWidth)

        path.arcTo(RectF(rect.right - mCornersRadius - mArrowWidth, rect.bottom - mCornersRadius,
                rect.right - mArrowWidth - strokeWidth, rect.bottom - strokeWidth), 0f, 90f)
        path.lineTo(rect.left + mArrowWidth + strokeWidth, rect.bottom - strokeWidth)

        path.arcTo(RectF(rect.left + strokeWidth, rect.bottom - mCornersRadius,
                mCornersRadius + rect.left, rect.bottom - strokeWidth), 90f, 90f)

        path.arcTo(RectF(rect.left + strokeWidth, rect.top + strokeWidth, mCornersRadius + rect.left, mCornersRadius + rect.top), 180f, 90f)
        path.close()
    }

    private fun initRightSquarePath(rect: RectF, path: Path, strokeWidth: Float) {

        path.moveTo(rect.left + strokeWidth, rect.top + strokeWidth)
        path.lineTo(rect.width() - mArrowWidth - strokeWidth, rect.top + strokeWidth)

        path.lineTo(rect.right - mArrowWidth - strokeWidth, mArrowPosition + strokeWidth / 2)
        path.lineTo(rect.right - strokeWidth - strokeWidth, mArrowPosition + mArrowHeight / 2)
        path.lineTo(rect.right - mArrowWidth - strokeWidth, mArrowPosition + mArrowHeight - strokeWidth / 2)

        path.lineTo(rect.right - mArrowWidth - strokeWidth, rect.bottom - strokeWidth)

        path.lineTo(rect.left + strokeWidth, rect.bottom - strokeWidth)
        path.lineTo(rect.left + strokeWidth, rect.top + strokeWidth)

        path.close()
    }


    private fun initBottomRoundedPath(rect: RectF, path: Path, strokeWidth: Float) {

        path.moveTo(rect.left + mCornersRadius + strokeWidth, rect.top + strokeWidth)
        path.lineTo(rect.width() - mCornersRadius - strokeWidth, rect.top + strokeWidth)
        path.arcTo(RectF(rect.right - mCornersRadius,
                rect.top + strokeWidth, rect.right - strokeWidth, mCornersRadius + rect.top), 270f, 90f)

        path.lineTo(rect.right - strokeWidth, rect.bottom - mArrowHeight - mCornersRadius - strokeWidth)
        path.arcTo(RectF(rect.right - mCornersRadius, rect.bottom - mCornersRadius - mArrowHeight,
                rect.right - strokeWidth, rect.bottom - mArrowHeight - strokeWidth), 0f, 90f)

        path.lineTo(rect.left + mArrowWidth + mArrowPosition - strokeWidth / 2, rect.bottom - mArrowHeight - strokeWidth)
        path.lineTo(rect.left + mArrowPosition + mArrowWidth / 2, rect.bottom - strokeWidth - strokeWidth)
        path.lineTo(rect.left + mArrowPosition + strokeWidth / 2, rect.bottom - mArrowHeight - strokeWidth)
        path.lineTo(rect.left + Math.min(mCornersRadius, mArrowPosition) + strokeWidth, rect.bottom - mArrowHeight - strokeWidth)

        path.arcTo(RectF(rect.left + strokeWidth, rect.bottom - mCornersRadius - mArrowHeight,
                mCornersRadius + rect.left, rect.bottom - mArrowHeight - strokeWidth), 90f, 90f)
        path.lineTo(rect.left + strokeWidth, rect.top + mCornersRadius + strokeWidth)
        path.arcTo(RectF(rect.left + strokeWidth, rect.top + strokeWidth, mCornersRadius + rect.left, mCornersRadius + rect.top), 180f, 90f)
        path.close()
    }

    private fun initBottomSquarePath(rect: RectF, path: Path, strokeWidth: Float) {

        path.moveTo(rect.left + strokeWidth, rect.top + strokeWidth)
        path.lineTo(rect.right - strokeWidth, rect.top + strokeWidth)
        path.lineTo(rect.right - strokeWidth, rect.bottom - mArrowHeight - strokeWidth)


        path.lineTo(rect.left + mArrowWidth + mArrowPosition - strokeWidth / 2, rect.bottom - mArrowHeight - strokeWidth)
        path.lineTo(rect.left + mArrowPosition + mArrowWidth / 2, rect.bottom - strokeWidth - strokeWidth)
        path.lineTo(rect.left + mArrowPosition + strokeWidth / 2, rect.bottom - mArrowHeight - strokeWidth)
        path.lineTo(rect.left + mArrowPosition + strokeWidth, rect.bottom - mArrowHeight - strokeWidth)


        path.lineTo(rect.left + strokeWidth, rect.bottom - mArrowHeight - strokeWidth)
        path.lineTo(rect.left + strokeWidth, rect.top + strokeWidth)
        path.close()
    }

}