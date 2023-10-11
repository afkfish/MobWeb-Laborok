package hu.bme.aut.android.simpledrawer.sqlite

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context) : SQLiteOpenHelper(context, DbConstants.DATABASE_NAME, null, DbConstants.DATABASE_VERSION) {

        override fun onCreate(sqLiteDatabase: SQLiteDatabase) {
            DbConstants.Lines.onCreate(sqLiteDatabase)
            DbConstants.Points.onCreate(sqLiteDatabase)
        }

        override fun onUpgrade(
            sqLiteDatabase: SQLiteDatabase,
            oldVersion: Int,
            newVersion: Int
        ) {
            DbConstants.Lines.onUpgrade(sqLiteDatabase, oldVersion, newVersion)
            DbConstants.Points.onUpgrade(sqLiteDatabase, oldVersion, newVersion)
        }
}