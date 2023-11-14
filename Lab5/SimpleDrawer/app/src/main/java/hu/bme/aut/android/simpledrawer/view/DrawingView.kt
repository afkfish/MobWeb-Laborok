package hu.bme.aut.android.simpledrawer.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hu.bme.aut.android.simpledrawer.model.Line
import hu.bme.aut.android.simpledrawer.model.Point

class DrawingView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    private lateinit var paint: Paint
    private var startPoint: Point? = null
    private var endPoint: Point? = null

    var lines = mutableListOf<Line>()
    var points = mutableListOf<Point>()

    init {
        initPaint()
    }

    private fun initPaint() {
        paint = Paint()
        paint.color = Color.GREEN
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 5F
    }

    fun setColor(color: Int) {
        paint.color = color
    }

    @SuppressWarnings("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        endPoint = Point(event.x, event.y)
        when (event.action) {
            MotionEvent.ACTION_DOWN -> startPoint = Point(event.x, event.y, paint.color)
            MotionEvent.ACTION_MOVE -> {
            }
            MotionEvent.ACTION_UP -> {
                when (currentDrawingStyle) {
                    DRAWING_STYLE_POINT -> addPointToTheList(endPoint!!.also { it.color = paint.color })
                    DRAWING_STYLE_LINE -> addLineToTheList(startPoint!!.also { it.color = paint.color }, endPoint!!)
                }
                startPoint = null
                endPoint = null
            }
            else -> return false
        }
        invalidate()
        return true
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (point in points) {
            drawPoint(canvas, point)
        }
        for (line in lines) {
            drawLine(canvas, line.start, line.end)
        }
        when (currentDrawingStyle) {
            DRAWING_STYLE_POINT -> drawPoint(canvas, endPoint)
            DRAWING_STYLE_LINE -> drawLine(canvas, startPoint, endPoint)
        }
    }

    private fun drawPoint(canvas: Canvas, point: Point?) {
        if (point == null) {
            return
        }
        canvas.drawPoint(point.x, point.y, paint.also { it.color = point.color })
    }

    private fun drawLine(canvas: Canvas, startPoint: Point?, endPoint: Point?) {
        if (startPoint == null || endPoint == null) {
            return
        }
        canvas.drawLine(
            startPoint.x,
            startPoint.y,
            endPoint.x,
            endPoint.y,
            paint.also { it.color = startPoint.color }
        )
    }

    private fun addPointToTheList(startPoint: Point) {
        points.add(startPoint)
    }

    private fun addLineToTheList(startPoint: Point, endPoint: Point) {
        lines.add(Line(startPoint, endPoint))
    }

    fun restoreObjects(points: MutableList<Point>?, lines: MutableList<Line>?) {
        points?.also { this.points = it }
        lines?.also { this.lines = it }
        invalidate()
    }

    companion object {
        const val DRAWING_STYLE_LINE = 0
        const val DRAWING_STYLE_POINT = 1
    }

    var currentDrawingStyle = DRAWING_STYLE_LINE
}
// NEPTUN: JOYAXJ