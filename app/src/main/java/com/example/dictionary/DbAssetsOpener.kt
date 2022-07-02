package com.alawail.dbapplication

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import com.readystatesoftware.sqliteasset.SQLiteAssetHelper

open class DbAssetsOpener(context: Context) : SQLiteAssetHelper (context, "dictionary.db", null, 1) {
    val db = this.readableDatabase

    protected fun runQuery(sql: String) : Cursor {
        return db.rawQuery(sql, null)
    }

    protected fun executeSQL(sql: String) {
        return db.execSQL(sql)
    }

    protected fun runQueryToGetValue(sql: String, defaultValue: String = "") : String {
        runQuery(sql).let {
            if (it.moveToFirst())
                return it.getString(0)
        }
        return defaultValue
    }

    @SuppressLint("Range")
    fun getValue(cursor: Cursor, colName: String) = cursor.getString(cursor.getColumnIndex(colName))

}