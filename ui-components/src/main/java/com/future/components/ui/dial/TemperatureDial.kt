package com.future.components.ui.dial

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.OvershootInterpolator
import androidx.core.content.ContextCompat
import com.future.components.ui.R

/**
 *
 * @Description:
 * @Author:         future
 * @CreateDate:     2022/6/30 18:36
 */
class TemperatureDial : View {
    private lateinit var paint: Paint
    private lateinit var rect: RectF
    private var pointLength = 0f //指针长度

    private var per = 0f //指数百分比

    private var perPoint = 0f //缓存(变化中)指针百分比

    private var perOld = 0f //变化前指针百分比

    private var length= 0f  //仪表盘半径

    private var r = 0f //大圆半径

    constructor(context: Context) : this(context,null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs,0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = width / 2 + 70
        initIndex(width / 2)
        //优化组件高度
        setMeasuredDimension(width, height)
    }

    private fun initIndex(specSize: Int) {
        r = specSize.toFloat()
        length = r / 4 * 3
        pointLength = -(r * 0.6).toFloat()
        per = 0f
        perOld = 0f
    }

    private fun init() {
        paint = Paint()
        rect = RectF()
    }

    override fun onDraw(canvas: Canvas) {
        setLayerType(LAYER_TYPE_SOFTWARE, null)
        //颜色指示的环
        initRing(canvas)
        //指针
        initPointer(canvas)
    }

    private fun initPointer(canvas: Canvas) {
        paint.color = ContextCompat.getColor(context, R.color.temperature_dial_pointer)
        canvas.restore()
        canvas.save()
        canvas.translate(r, r)
        val change: Float = if (perPoint < 1) {
            perPoint * 180
        } else {
            180f
        }

        //根据参数得到旋转角度
        canvas.rotate(-90 + change, 0f, 0f)

        //绘制三角形形成指针
        val path = Path()
        path.moveTo(0f, pointLength)
        path.lineTo(-10f, 0f)
        path.lineTo(10f, 0f)
        path.lineTo(0f, pointLength)
        path.close()
        canvas.drawPath(path, paint)
        paint.color = ContextCompat.getColor(context, R.color.temperature_dial_inside_radio)
        canvas.drawCircle(0f, 0f, 20f, paint)
        paint.color = ContextCompat.getColor(context, R.color.temperature_dial_outside_radio)
        canvas.drawCircle(0f, 0f, 40f, paint)
    }

    private fun initRing(canvas: Canvas) {
        paint.isAntiAlias = true
        paint.strokeWidth = 2f
        canvas.save()
        canvas.translate(r, r)

        //前100红黄渐变圆环
        paint.style = Paint.Style.FILL
        paint.color = ContextCompat.getColor(context, R.color.temperature_dial_blue)
        rect = RectF(-length, -length, length, length)
        canvas.drawArc(rect, 180f, 60f, true, paint)
        paint.color = ContextCompat.getColor(context, R.color.temperature_dial_orange)
        canvas.drawArc(rect, 240f, 60f, true, paint)
        paint.color = ContextCompat.getColor(context, R.color.temperature_dial_red)
        canvas.drawArc(rect, 300f, 60f, true, paint)
        canvas.restore()
        canvas.save()
        canvas.translate(r, r)

        //内部背景色填充
        paint.color = ContextCompat.getColor(context, R.color.temperature_dial_bg)
        paint.shader = null
        rect = RectF(-(length - length / 3f - 2), -(length / 3f * 2f - 2), length - length / 3f - 2, length / 3f * 2f - 2)
        canvas.drawArc(rect, 0f, 360f, false, paint)
        canvas.restore()
        canvas.save()
        canvas.translate(r, r)
        paint.shader = null
        paint.color = ContextCompat.getColor(context, R.color.temperature_dial_blue)
        val rc = r / 8 * 1 + 2
        canvas.drawCircle(-(r / 2) - rc + 2, 0f, rc, paint)
        canvas.drawCircle(-rc * 3 + rc / 2, -rc * 4 + 4, rc, paint)
        paint.color = ContextCompat.getColor(context, R.color.temperature_dial_red)
        canvas.drawCircle(r / 2 + rc - 2, 0f, rc, paint)
        paint.color = ContextCompat.getColor(context, R.color.temperature_dial_orange)
        canvas.drawCircle(rc * 3 - rc / 2, -rc * 4 + 4, rc, paint)
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