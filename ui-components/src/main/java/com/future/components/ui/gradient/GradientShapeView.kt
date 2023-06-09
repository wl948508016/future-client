package com.future.components.ui.gradient

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.util.AttributeSet
import android.view.View
import androidx.core.content.ContextCompat
import com.future.components.ui.R


/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2023/5/9 09:26
 */
class GradientShapeView:View {

    private var colorStart = 0
    private var colorEnd = 0
    private var colorAfterStart = 0
    private var colorBeforeEnd = 0
    private var backGroundRect: RectF? = null
    private var backGradient: LinearGradient? = null
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintText = Paint()
    private var radius = 0f

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?):this(context,attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):super(context,attrs,defStyleAttr){
        init(context,attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?){
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientShapeView)
        colorStart = typedArray.getColor(R.styleable.GradientShapeView_start_color, ContextCompat.getColor(context, R.color.brain_result_start))
        colorAfterStart = typedArray.getColor(R.styleable.GradientShapeView_after_start_color, ContextCompat.getColor(context, R.color.brain_result_after_start))
        colorBeforeEnd = typedArray.getColor(R.styleable.GradientShapeView_before_end_color, ContextCompat.getColor(context, R.color.brain_result_before_end))
        colorEnd = typedArray.getColor(R.styleable.GradientShapeView_end_color, ContextCompat.getColor(context, R.color.brain_result_end))
        radius = typedArray.getDimension(R.styleable.GradientShapeView_radius, resources.getDimensionPixelSize(R.dimen.future_common_10_dp).toFloat())

        typedArray.recycle()
        //设置抗锯齿
        paint.isAntiAlias = true
        //设置防抖动
        paint.isDither = true
        paint.style = Paint.Style.FILL
        paintText.isAntiAlias = true
        paintText.isDither = true
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        backGroundRect = RectF(0f, 0f, w.toFloat(), h.toFloat())
        backGradient = LinearGradient(0f, 0f, w.toFloat(), 0f, intArrayOf(colorStart, colorAfterStart, colorBeforeEnd, colorEnd),null, Shader.TileMode.CLAMP)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.shader = backGradient
        backGroundRect?.let { canvas.drawRoundRect(it, radius, radius, paint) }
    }
}