package com.future.components.ui.text

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import com.future.components.ui.R

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/8/29 10:06
 */
class GradientTextView: AppCompatTextView {

    private var startColor = 0
    private var centerColor = 0
    private var endColor = 0
    private var bold = false

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?):this(context,attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int):super(context,attrs,defStyleAttr){
        init(context,attrs)
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.GradientTextView)
        bold = typedArray.getBoolean(R.styleable.GradientTextView_gradient_bold, false)
        startColor = typedArray.getColor(R.styleable.GradientTextView_gradient_start_color, ContextCompat.getColor(context, R.color.gradient_text_start))
        centerColor = typedArray.getColor(R.styleable.GradientTextView_gradient_start_color, ContextCompat.getColor(context, R.color.gradient_text_center))
        endColor = typedArray.getColor(R.styleable.GradientTextView_gradient_end_color, ContextCompat.getColor(context, R.color.gradient_text_end))
        typedArray.recycle()
        if (bold) {
            paint.isFakeBoldText = true
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (changed) {
            paint.shader = LinearGradient(0F, 0F, 0F, height.toFloat(), intArrayOf(startColor,centerColor, endColor), floatArrayOf(0F,0.2F,1.0F), Shader.TileMode.CLAMP)
        }
    }
}