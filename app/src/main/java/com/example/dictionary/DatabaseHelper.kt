package com.example.dictionary

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

const val database = "dictionary.db"
const val tableName = "words"
const val col_id = "id"
const val col_word = "word"
const val col_type = "type"
const val col_meaning = "meaning"
const val col_sentence = "example sentence"
const val col_history = "history"
const val col_favorites = "favorites"

class DataBaseHelper(var context: Context): SQLiteAssetHelper(context, database,null, 1) {


/*    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $tableName ($col_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " $col_word VARCHAR(256)," +
                    " $col_meaning VARCHAR(256)," +
                    " $col_sentence VARCHAR(256)," +
                    " $col_history INTEGER DEFAULT 0," +
                    " $col_favorites INTEGER DEFAULT 0)"
        )
    }*/

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        super.onUpgrade(db, oldVersion, newVersion)
        db?.execSQL("DROP TABLE IF EXISTS $tableName")
        onCreate(db)
    }

}
