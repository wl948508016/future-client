package com.future.components.ui.dial

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.content.ContextCompat
import com.future.components.ui.R
import kotlin.math.ceil

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/7/1 16:02
 */
class BloodDial : View {
    private lateinit var paint: Paint
    private lateinit var textPaint: Paint
    private lateinit var strokePain: Paint
    private lateinit var rect: RectF
    private var per = 0f //指数百分比

    private var perPoint = 0f //缓存(变化中)指针百分比

    private var perOld = 0f //变化前指针百分比

    private var length = 0f //仪表盘半径

    private var r = 0f //大圆半径

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        initIndex(width / 2)
        //优化组件高度
        setMeasuredDimension(width, width)
    }

    private fun initIndex(specSize: Int) {
        r = specSize.toFloat()
        length = r / 4 * 3
        per = 0f
        perOld = 0f
    }

    private fun init() {
        paint = Paint()
        rect = RectF()
        textPaint = Paint()
        strokePain = Paint()
    }

    override fun onDraw(canvas: Canvas) {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        //颜色指示的环
        initRing(canvas)
        //提示内容
        initText(canvas)
    }

    private fun initText(canvas: Canvas) {
        //抗锯齿
        canvas.drawFilter = PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
        canvas.restore()
        canvas.save()
        canvas.translate(r, r)
        textPaint.strokeWidth = 1f
        textPaint.isAntiAlias = true
        textPaint.textSize = context.resources.getDimensionPixelSize(R.dimen.future_text_16).toFloat()
        textPaint.color = ContextCompat.getColor(context, R.color.white)
        textPaint.isFakeBoldText = true
        textPaint.textAlign = Paint.Align.RIGHT

        //判断指数变化及颜色设定
        val perC = (per * 100).toInt()
        var swidth = textPaint.measureText(perC.toString())
        val fontMetrics = textPaint.fontMetrics
        var sheight = ceil((fontMetrics.descent - fontMetrics.ascent).toDouble()).toInt().toFloat()
        //计算偏移量 是的数字和百分号整体居中显示
        swidth -= (swidth + 22) / 2
        sheight /= 3
        canvas.translate(swidth, sheight)
        canvas.drawText(perC.toString(), 0f, 0f, textPaint)
        textPaint.textSize = context.resources.getDimensionPixelSize(R.dimen.future_text_12).toFloat()
        textPaint.isFakeBoldText = false
        textPaint.textAlign = Paint.Align.LEFT
        canvas.drawText("%", 10f, 0f, textPaint)
        canvas.restore()
        canvas.save()
        canvas.translate(canvas.width / 2f, r)
        canvas.translate(-length / 2 / 4 * 5 - 10, length / 2 / 4 * 5 - 10)
        textPaint.textSize = context.resources.getDimensionPixelSize(R.dimen.future_text_8).toFloat()
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.color = ContextCompat.getColor(context, R.color.blood_text_color)
        canvas.drawText("0", 0f, 0f, textPaint)
        canvas.restore()
        canvas.save()
        canvas.translate(canvas.width / 2f, r)
        canvas.translate(length / 2 / 4 * 5 + 10, length / 2 / 4 * 5 - 10)
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.color = ContextCompat.getColor(context, R.color.blood_text_color)
        canvas.drawText("100", 0f, 0f, textPaint)
    }

    private fun initRing(canvas: Canvas) {
        paint.isAntiAlias = true
        paint.strokeWidth = 2f
        canvas.save()
        canvas.translate(r, r)
        paint.style = Paint.Style.FILL
        paint.color = ContextCompat.getColor(context, R.color.blood_radio_bg)
        paint.shader = null
        rect = RectF(-length, -length, length, length)
        canvas.drawArc(rect, 0f, 180f, true, paint)

        canvas.restore()
        canvas.save()
        canvas.translate(r, r)
        rect = RectF(-length - 10, -length - 10, length + 10, length + 10)
        strokePain.color = ContextCompat.getColor(context, R.color.blood_radio_shadow_stroke)
        strokePain.strokeWidth = 20f
        strokePain.style = Paint.Style.STROKE
        canvas.drawArc(rect, 135f, 270f, false, strokePain)
        canvas.drawArc(rect, 45f, 90f, false, strokePain)

        canvas.restore()
        canvas.save()
        canvas.translate(r, r)
        strokePain = Paint(paint)
        strokePain.color = ContextCompat.getColor(context, R.color.blood_radio_stroke)
        strokePain.strokeWidth = 5f
        strokePain.shader = null
        strokePain.style = Paint.Style.STROKE
        rect = RectF(-length - 5, -length - 5, length + 5, length + 5)
        canvas.drawArc(rect, 134f, 272f, true, strokePain)

        paint.style = Paint.Style.FILL
        val colors = intArrayOf(ContextCompat.getColor(context, R.color.blood_progress_start), ContextCompat.getColor(context, R.color.blood_progress_end))
        val positions = floatArrayOf(0f, 0.5f)
        val sweepGradient = SweepGradient(0F, 0F, colors, positions)
        paint.shader = sweepGradient
        canvas.rotate(135f)
        rect = RectF(-length, -length, length, length)
        canvas.drawArc(rect, 0f, perPoint * 270f, true, paint)

        canvas.restore()
        canvas.save()
        canvas.translate(r, r)
        paint.shader = null
        paint.color =ContextCompat.getColor(context,R.color.blood_progress_start)
        canvas.rotate(135f)
        canvas.drawArc(rect, perPoint * 270f, 270f - perPoint * 270f, true, paint)

        canvas.restore()
        canvas.save()
        canvas.translate(r, r)
        //内部背景色填充
        paint.color = ContextCompat.getColor(context, R.color.blood_bg_color)
        paint.shader = null
        rect = RectF(-(length - length / 3f - 2), -(length / 3f * 2f - 2), length - length / 3f - 2, length / 3f * 2f - 2)
        strokePain.color = ContextCompat.getColor(context, R.color.blood_radio_inside_stroke)
        canvas.rotate(35f)
        canvas.drawArc(rect, 100f, 270f, true, strokePain)
        canvas.drawArc(rect, 0f, 360f, true, paint)

        strokePain.color = ContextCompat.getColor(context, R.color.blood_radio_stroke)
        strokePain.strokeWidth = 5f
        rect = RectF(-(length - length / 3f - 25), -(length / 3f * 2f - 25), length - length / 3f - 25, length / 3f * 2f - 25)
        canvas.drawArc(rect, 100f, 270f, true, strokePain)
        canvas.drawArc(rect, 0f, 360f, true, paint)

        canvas.restore()
        canvas.save()
        canvas.translate(r, r)
        rect = RectF(-(length - length / 2f - 2), -(length / 2f - 2), length - length / 2f - 2, length / 2f - 2)
        val radialGradient = RadialGradient(0F, 0F, length, ContextCompat.getColor(context, R.color.blood_progress_inside_radio_start), ContextCompat.getColor(context, R.color.blood_progress_inside_radio_end), Shader.TileMode.CLAMP)
        paint.shader = radialGradient
        canvas.drawArc(rect, 0f, 360f, true, paint)
    }

    fun changePer(per: Float) {
        perOld = this.per
        this.per = per
        val va = ValueAnimator.ofFloat(perOld, per)
        va.duration = 1000
        va.interpolator = OvershootInterpolator()
        va.addUpdateListener { animation: ValueAnimator ->
            perPoint = animation.animatedValue as Float
            invalidate()
        }
        va.start()
    }
}