package com.example.projetorobert

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "Calculations.db"
        private const val DATABASE_VERSION = 1

        // Nome da tabela e colunas
        private const val TABLE_NAME = "history"
        private const val COLUMN_ID = "id"
        private const val COLUMN_EXPRESSION = "expression"
        private const val COLUMN_RESULT = "result"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Criação da tabela
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,"
                + "$COLUMN_EXPRESSION TEXT,"
                + "$COLUMN_RESULT TEXT)")
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Atualização da tabela, se necessário
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Inserir um novo cálculo no banco de dados
    fun insertCalculation(expression: String, result: String): Long {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(COLUMN_EXPRESSION, expression)
        contentValues.put(COLUMN_RESULT, result)
        return db.insert(TABLE_NAME, null, contentValues)
    }

    // Recuperar todos os cálculos do banco de dados
    fun getAllCalculations(): List<String> {
        val calculations = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val expression = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_EXPRESSION))
                val result = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RESULT))
                calculations.add("$expression = $result")
            } while (cursor.moveToNext())
        }
        cursor.close()
        return calculations
    }
}
