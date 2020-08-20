package com.mrspd.onsiteround2

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View

class ClockView1 : View {
    private var heightt = 0
    private var widthh = 0
    private var padding = 0
    private var fontsize = 0
    private val numeralSpacing = 0
    private var handTruncation = 0
    private var radius = 0
    private var paint: Paint? = null
    private var isInit = false
    private val numbers = intArrayOf(5, 10, 15, 20, 25, 30, 35, 40, 45, 50, 55, 60)
    private val numbers2 = intArrayOf(1, 2, 3, 4, 5, 6, 7, 8)
    private val rect = Rect()

    constructor(context: Context?) : super(context) {}
    constructor(context: Context?, attrs: AttributeSet?) : super(
        context,
        attrs
    ) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr) {
    }

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes) {
    }

    private fun initClock() {
        heightt = getHeight()
        widthh = getWidth()
        padding = numeralSpacing + 50
        fontsize = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_SP,
            20f,
            resources.displayMetrics
        ).toInt()
        val min = Math.min(height, width)
        radius = min / 2 - padding
        handTruncation = min / 20
        paint = Paint()
        isInit = true
    }

    override fun onDraw(canvas: Canvas) {
        if (!isInit) {
            initClock()
        }
        canvas.drawColor(Color.BLACK)
        drawClockCircle(canvas, true)
        drawClockCircle(canvas, false)
        drawClckCenter(canvas, true)
        drawClckCenter(canvas, false)
        drawNumbers(canvas, true)
        drawNumbers(canvas, false)
        drawSecondHAnd(canvas, MainActivity.sec.toDouble())
        drawMinHand(canvas, MainActivity.min)


        // postInvalidateDelayed(500);
        invalidate()
    }

    private fun drawMinHand(canvas: Canvas, min: Int) {
        val angle = Math.PI * min / 16 - Math.PI / 2
        val handRadius = (radius - handTruncation) / 4
        canvas.drawLine(
            width / 3.toFloat(),
            height / 2.toFloat(),
            (width / 3 + Math.cos(angle) * handRadius).toFloat(),
            (height / 2 + Math.sin(angle) * handRadius).toFloat(),
            paint!!
        )
    }

    private fun drawSecondHAnd(canvas: Canvas, loc: Double) {
        val angle = Math.PI * loc / 120 - Math.PI / 2
        val handRadius = radius - handTruncation
        canvas.drawLine(
            width / 2.toFloat(),
            height / 2.toFloat(),
            (width / 2 + Math.cos(angle) * handRadius).toFloat(),
            (height / 2 + Math.sin(angle) * handRadius).toFloat(),
            paint!!
        )
    }

    private fun drawNumbers(canvas: Canvas, isMin: Boolean) {
        for (number in numbers2) {
            val tmp = number.toString()
            paint!!.getTextBounds(tmp, 0, tmp.length, rect)
            val angle = Math.PI / 4  * ((number) - 3)
            if (!isMin) {
                paint!!.textSize = fontsize / 2.toFloat()
                val x =
                    (width / 3 + Math.cos(angle) * radius / 3.5 - rect.width() / 2).toInt()
                val y =
                    (height / 2 + Math.sin(angle) * radius / 3.5 + rect.height() / 2).toInt()
                canvas.drawText(tmp, x.toFloat(), y.toFloat(), paint!!)
            }
        }
        for (number in numbers) {
            val tmp = number.toString()
            paint!!.getTextBounds(tmp, 0, tmp.length, rect)
            val angle = Math.PI / 6 * ((number / 5) - 3)
            if (isMin) {
                paint!!.textSize = fontsize.toFloat()
                val x =
                    (width / 2 + Math.cos(angle) * radius - rect.width() / 2).toInt()
                val y =
                    (height / 2 + Math.sin(angle) * radius + rect.height() / 2).toInt()
                canvas.drawText(tmp, x.toFloat(), y.toFloat(), paint!!)
            }
        }
    }

    private fun drawClckCenter(canvas: Canvas, isMin: Boolean) {
        paint!!.style = Paint.Style.FILL
        if (!isMin) canvas.drawCircle(
            width / 2.toFloat(),
            height / 2.toFloat(),
            12f,
            paint!!
        ) else canvas.drawCircle(width / 3.toFloat(), height / 2.toFloat(), 8f, paint!!)
    }

    private fun drawClockCircle(canvas: Canvas, isMin: Boolean) {
        paint!!.reset()
        paint!!.color = Color.WHITE
        paint!!.strokeWidth = 5f
        paint!!.style = Paint.Style.STROKE
        paint!!.isAntiAlias = true
        if (!isMin) canvas.drawCircle(
            width / 2.toFloat(),
            height / 2.toFloat(),
            radius + padding - 10.toFloat(),
            paint!!
        ) else canvas.drawCircle(
            width / 3.toFloat(),
            height / 2.toFloat(),
            (radius + padding - 10) / 3.toFloat(),
            paint!!
        )
    }
}