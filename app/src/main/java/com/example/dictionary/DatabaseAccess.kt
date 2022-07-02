package com.example.dictionary

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast
import com.alawail.dbapplication.DbAssetsOpener

class DatabaseAccess(context: Context): DbAssetsOpener(context){
    private lateinit var database :SQLiteDatabase
    private var openHelper: SQLiteOpenHelper = DataBaseHelper(context)

    companion object{
        @SuppressLint("StaticFieldLeak")
        lateinit var instance: DatabaseAccess
        fun getInstance(context: Context): DatabaseAccess{
            if(instance == null){
                instance = DatabaseAccess(context)
            }
            return instance
        }
    }

    //Search in the database
    @SuppressLint("Range", "Recycle")
    fun getWords(search: String): ArrayList<Word>{
        val words = ArrayList<Word>()
        lateinit var cursor: Cursor
        if(search != "")
            cursor = runQuery("SELECT * FROM $tableName WHERE word like '${search}%'")
        else return words
        fun getValue(colName: String) = getValue(cursor, colName)
        if (cursor.moveToFirst()){
            do {
                words.add(Word(cursor.getInt(cursor.getColumnIndex(col_id)),
                    getValue(col_word),
                    getValue(col_type),
                    getValue(col_meaning),
                    getValue(col_sentence),
                    getValue(col_history),
                    getValue(col_favorites)))
            } while (cursor.moveToNext())
        }
        return words
    }

    //get history
    @SuppressLint("Range", "Recycle")
    fun getHistory(): ArrayList<Word>{
        val words = ArrayList<Word>()
        val cursor: Cursor = runQuery("SELECT * FROM $tableName WHERE history = 1")
        cursor.moveToFirst()
        fun getValue(colName: String) = getValue(cursor, colName)
        if (cursor.moveToFirst()){
            do {
                words.add(Word(cursor.getInt(cursor.getColumnIndex(col_id)),
                    getValue(col_word),
                    getValue(col_type),
                    getValue(col_meaning),
                    getValue(col_sentence),
                    getValue(col_history),
                    getValue(col_favorites)))
            } while (cursor.moveToNext())
        }
        return words
    }

    //get favorites
    @SuppressLint("Range", "Recycle")
    fun getFavorites(): ArrayList<Word>{
        val words = ArrayList<Word>()
        val cursor = runQuery("SELECT * FROM $tableName WHERE favorites = 1")
        fun getValue(colName: String) = getValue(cursor, colName)
        if (cursor.moveToFirst()){
            do {
                words.add(Word(cursor.getInt(cursor.getColumnIndex(col_id)),
                    getValue(col_word),
                    getValue(col_type),
                    getValue(col_meaning),
                    getValue(col_sentence),
                    getValue(col_history),
                    getValue(col_favorites)))
            } while (cursor.moveToNext())
        }
        return words
    }

    //add to history
    fun addToHistory(position: Int){
        executeSQL("UPDATE $tableName SET $col_history = 1 WHERE $col_id = $position")
    }

    //clear history
    fun clearHistory(){
        executeSQL("UPDATE $tableName SET $col_history = 0")
    }

    //add to favorites
    fun addToFavorites(position: Int){
        executeSQL("UPDATE $tableName SET $col_favorites = 1 WHERE $col_id = $position")
    }

    //remove from favorites
    fun removeFromFavorites(position: Int){
        executeSQL("UPDATE $tableName SET $col_favorites = 0 WHERE $col_id = $position")
    }

    //clear favorites
    fun clearFavorites(){
        executeSQL("UPDATE $tableName SET $col_favorites = 0")
    }
}