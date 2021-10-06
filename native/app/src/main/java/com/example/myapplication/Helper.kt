package com.example.myapplication

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import org.jetbrains.anko.toast

private const val SQL_CREATE_ENTRIES =
    """CREATE TABLE IF NOT EXISTS ${MovieContract.MovieEntry.DB_TABLE} (
            ${MovieContract.MovieEntry.COLUMN_ID} INTEGER PRIMARY KEY AUTOINCREMENT,
            ${MovieContract.MovieEntry.COLUMN_TITLE} TEXT,
            ${MovieContract.MovieEntry.COLUMN_YEAR} TEXT,
            ${MovieContract.MovieEntry.COLUMN_GENRE} TEXT,
            ${MovieContract.MovieEntry.COLUMN_DIRECTOR} TEXT,
            ${MovieContract.MovieEntry.COLUMN_STARS} TEXT,
            ${MovieContract.MovieEntry.COLUMN_FAVORTITECHARACTER} TEXT,
            ${MovieContract.MovieEntry.COLUMN_MEMORABLEQUOTES} TEXT,
            ${MovieContract.MovieEntry.COLUMN_RATING} TEXT,
            ${MovieContract.MovieEntry.COLUMN_OPINION} TEXT)
    """

private const val SQL_DELETE_ENTRIES =
    "DROP TABLE IF EXISTS ${MovieContract.MovieEntry.DB_TABLE}"

class DatabaseHelper(context : Context) :
        SQLiteOpenHelper(
            context,
            MovieContract.DB_NAME,
            null,
            MovieContract.DB_VERSION
        ) {

    var context: Context? = context

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(SQL_CREATE_ENTRIES)
        context?.toast("database is created")
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(SQL_DELETE_ENTRIES)
    }

}