package com.oop.laba2.donroute.customView

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.graphics.RectF
import com.oop.laba2.donroute.R


/**
 * TODO: document your custom view class.
 */
class RouteCustomView : View {

    private var _circleColor: Int = Color.RED // TODO: use a default from R.color...
    private var _roadColor: Int = resources.getColor(R.color.main_color)// TODO: use a default from R.color...

    private var _state:Int = 1




    var state: Int
        get() = _state
        set(value) {
            _state = value
            invalidate()
        }


    var circleColor: Int
        get() = _circleColor
        set(value) {
            _circleColor = value
            invalidate()
        }

    var roadColor: Int
        get() = _roadColor
        set(value) {
            _roadColor = value
            invalidate()
        }


    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.RouteCustomView, defStyle, 0
        )
        _state = a.getInt(
            R.styleable.RouteCustomView_state,
            state
        )

        _circleColor = a.getColor(
            R.styleable.RouteCustomView_circleColor,
            circleColor
        )

        _roadColor = a.getColor(
            R.styleable.RouteCustomView_roadColor,
            roadColor
        )

    }

    val roadRect = Rect()
    val centerCircle = RectF()
    val paint = Paint()
    val bigCircle = RectF()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paddingLeft = paddingLeft
        val paddingTop = paddingTop
        val paddingRight = paddingRight
        val paddingBottom = paddingBottom

        val contentWidth = width - paddingLeft - paddingRight
        val contentHeight = height - paddingTop - paddingBottom



        val centerCircleRadius = canvas.width/5f
        when(state){
            0->{
                //road
                roadRect.set(
                    0,
                    canvas.height/2,
                    canvas.width,
                    canvas.height)
                paint.color = _roadColor
                paint.style = Paint.Style.FILL
                canvas.drawRect(roadRect, paint)

                //bigCircle
                bigCircle.set(
                    0f,
                    canvas.height/2-canvas.width/2f,
                    canvas.width.toFloat(),
                    canvas.height/2+canvas.width/2f,
                )
                paint.color = circleColor
                canvas.drawOval(bigCircle,paint)
            }
            1->{
                //road
                roadRect.set(0, 0, canvas.width, canvas.height)
                paint.color = _roadColor
                paint.style = Paint.Style.FILL
                canvas.drawRect(roadRect, paint)

                //centerCircle
                centerCircle.set(
                    canvas.width/2-centerCircleRadius,
                    canvas.height/2-centerCircleRadius,
                    canvas.width/2+centerCircleRadius,
                    canvas.height/2+centerCircleRadius
                )
                paint.color = resources.getColor(R.color.roadCenterDot)
                canvas.drawOval(centerCircle,paint)
            }
            2->{
                //road
                roadRect.set(
                    0,
                    0,
                    canvas.width,
                    canvas.height/2)
                paint.color = _roadColor
                paint.style = Paint.Style.FILL
                canvas.drawRect(roadRect, paint)

                //bigCircle
                bigCircle.set(
                    0f,
                    canvas.height/2-canvas.width/2f,
                    canvas.width.toFloat(),
                    canvas.height/2+canvas.width/2f,
                )
                paint.color = circleColor
                canvas.drawOval(bigCircle,paint)
            }
        }

    }
}