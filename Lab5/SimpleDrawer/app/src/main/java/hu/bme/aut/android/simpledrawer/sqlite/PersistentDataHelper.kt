package hu.bme.aut.android.simpledrawer.sqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import hu.bme.aut.android.simpledrawer.model.Line
import hu.bme.aut.android.simpledrawer.model.Point

// NEPTUN: JOYAXJ
class PersistentDataHelper(context: Context) {
    private var database: SQLiteDatabase? = null
    private val dbHelper: DBHelper = DBHelper(context)

    private val pointColumns = arrayOf(
        DbConstants.Points.Columns.ID.name,
        DbConstants.Points.Columns.COORD_X.name,
        DbConstants.Points.Columns.COORD_Y.name,
        DbConstants.Points.Columns.COLOR.name
    )

    private val lineColumns = arrayOf(
        DbConstants.Lines.Columns.ID.name,
        DbConstants.Lines.Columns.START_X.name,
        DbConstants.Lines.Columns.START_Y.name,
        DbConstants.Lines.Columns.END_X.name,
        DbConstants.Lines.Columns.END_Y.name,
        DbConstants.Lines.Columns.COLOR.name
    )

    @Throws(SQLiteException::class)
    fun open() {
        database = dbHelper.writableDatabase
    }

    fun close() {
        dbHelper.close()
    }

    fun persistPoints(points: List<Point>) {
        clearPoints()
        for (point in points) {
            val values = ContentValues()
            values.put(DbConstants.Points.Columns.COORD_X.name, point.x)
            values.put(DbConstants.Points.Columns.COORD_Y.name, point.y)
            values.put(DbConstants.Points.Columns.COLOR.name, point.color)
            database!!.insert(DbConstants.Points.DATABASE_TABLE, null, values)
        }
    }
    // NEPTUN: JOYAXJ

    fun restorePoints(): MutableList<Point> {
        val points: MutableList<Point> = ArrayList()
        val cursor: Cursor =
            database!!.query(DbConstants.Points.DATABASE_TABLE, pointColumns, null, null, null, null, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val point: Point = cursorToPoint(cursor)
            points.add(point)
            cursor.moveToNext()
        }
        cursor.close()
        return points
    }

    fun clearPoints() {
        database!!.delete(DbConstants.Points.DATABASE_TABLE, null, null)
    }

    private fun cursorToPoint(cursor: Cursor): Point {
        val point = Point()
        point.x =cursor.getFloat(DbConstants.Points.Columns.COORD_X.ordinal)
        point.y =cursor.getFloat(DbConstants.Points.Columns.COORD_Y.ordinal)
        point.color =cursor.getInt(DbConstants.Points.Columns.COLOR.ordinal)
        return point
    }

    fun persistLines(lines: List<Line>) {
        clearLines()
        for (line in lines) {
            val values = ContentValues()
            values.put(DbConstants.Lines.Columns.START_X.name, line.start.x)
            values.put(DbConstants.Lines.Columns.START_Y.name, line.start.y)
            values.put(DbConstants.Lines.Columns.END_X.name, line.end.x)
            values.put(DbConstants.Lines.Columns.END_Y.name, line.end.y)
            values.put(DbConstants.Lines.Columns.COLOR.name, line.start.color)
            database!!.insert(DbConstants.Lines.DATABASE_TABLE, null, values)
        }
    }

    fun restoreLines(): MutableList<Line> {
        val lines: MutableList<Line> = ArrayList()
        val cursor: Cursor =
            database!!.query(DbConstants.Lines.DATABASE_TABLE, lineColumns, null, null, null, null, null)
        cursor.moveToFirst()
        while (!cursor.isAfterLast) {
            val line: Line = cursorToLine(cursor)
            lines.add(line)
            cursor.moveToNext()
        }
        cursor.close()
        return lines
    }

    fun clearLines() {
        database!!.delete(DbConstants.Lines.DATABASE_TABLE, null, null)
    }

    private fun cursorToLine(cursor: Cursor): Line {
        val startPoint = Point(
            cursor.getFloat(DbConstants.Lines.Columns.START_X.ordinal),
            cursor.getFloat(DbConstants.Lines.Columns.START_Y.ordinal),
            cursor.getInt(DbConstants.Lines.Columns.COLOR.ordinal)
        )
        val endPoint = Point(
            cursor.getFloat(DbConstants.Lines.Columns.END_X.ordinal),
            cursor.getFloat(DbConstants.Lines.Columns.END_Y.ordinal)
        )
        val color = cursor.getInt(DbConstants.Lines.Columns.COLOR.ordinal)
        return Line(startPoint, endPoint, color)
    }
}