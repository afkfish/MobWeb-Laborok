package hu.bme.aut.android.simpledrawer.sqlite

import android.database.sqlite.SQLiteDatabase
import android.util.Log

object DbConstants {

    const val DATABASE_NAME = "simpledrawer.db"
    const val DATABASE_VERSION = 1

    object Points {
        const val DATABASE_TABLE = "points"

        enum class Columns {
            ID, COORD_X, COORD_Y, COLOR
        }

        private val DATABASE_CREATE = """create table if not exists $DATABASE_TABLE (
        ${Columns.ID.name} integer primary key autoincrement,
        ${Columns.COORD_X} real not null,
        ${Columns.COORD_Y} real not null,
        ${Columns.COLOR} integer not null
        );"""

        private const val DATABASE_DROP = "drop table if exists $DATABASE_TABLE;"

        fun onCreate(database: SQLiteDatabase) {
            database.execSQL(DATABASE_CREATE)
        }

        fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.w(
                Points::class.java.name,
                "Upgrading from version $oldVersion to $newVersion"
            )
            database.execSQL(DATABASE_DROP)
            onCreate(database)
        }
    }

    object Lines {
        const val DATABASE_TABLE = "lines"

        enum class Columns {
            ID, START_X, START_Y, END_X, END_Y, COLOR
        }

        private val DATABASE_CREATE ="""create table if not exists $DATABASE_TABLE (
        ${Columns.ID.name} integer primary key autoincrement,
        ${Columns.START_X} real not null,
        ${Columns.START_Y} real not null,
        ${Columns.END_X} real not null,
        ${Columns.END_Y} real not null,
        ${Columns.COLOR} integer not null
    
        );"""

        private const val DATABASE_DROP = "drop table if exists $DATABASE_TABLE;"

        fun onCreate(database: SQLiteDatabase) {
            database.execSQL(DATABASE_CREATE)
        }

        fun onUpgrade(database: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            Log.w(
                Lines::class.java.name,
                "Upgrading from version $oldVersion to $newVersion"
            )
            database.execSQL(DATABASE_DROP)
            onCreate(database)
        }
    }
}