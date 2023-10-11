package hu.bme.aut.android.simpledrawer.model

data class Line(
    var start: Point,
    var end: Point,
    var color: Int = start.color
)
