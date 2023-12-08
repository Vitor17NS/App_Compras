package com.example.p2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    companion object{
        private val DATABASE_NAME = "CADASTRO_DE_PRODUTO"
        private val DATABASE_VERSION = 1

        val TABLE_NAME = "produto"
        val DESCRICAO_PROD = "descricao"
        val ID_PROD = "id"
        val VALOR_PROD = "valor"
        val QTDE_PROD = "quantidade"
    }

    override fun onCreate(db: SQLiteDatabase) {
        val query = ("CREATE TABLE $TABLE_NAME ( " +
                "$ID_PROD INTEGER PRIMARY KEY, " +
                "$DESCRICAO_PROD TEXT, " +
                "$VALOR_PROD TEXT, " +
                "$QTDE_PROD TEXT)")

        db.execSQL(query)
    }


    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int){
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun addProd(name: String, valor: String, quantidade: String) {
        val values = ContentValues()

        values.put(DESCRICAO_PROD, name)
        values.put(VALOR_PROD, valor)
        values.put(QTDE_PROD, quantidade)

        val db = this.writableDatabase

        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getProd(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun deleteAllData() {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, null, null)
        db.close()
    }
}