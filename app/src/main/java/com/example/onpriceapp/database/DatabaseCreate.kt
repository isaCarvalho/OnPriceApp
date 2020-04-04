package com.example.onpriceapp.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns

class DatabaseCreate (context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)
{
    object  FeedReaderContract
    {
        object FeedEntry : BaseColumns
        {
            const val TABLE_PRODUCTS = "products"
            const val NAME_PRODUCTS = "name"
            const val CATEGORY_PRODUCTS = "category"
            const val PRICE_PRODUCTS = "price"
            const val QT_PRODUCTS = "quantity"
            const val UNITY_PRODUCTS = "unity"
            const val ID_STORE = "id_store"

            const val TABLE_STORE = "stores"
            const val NAME_STORE = "name"
            const val PASSOWORD_STORE = "password"
            const val CNPJ_STORE = "cnpj"
            const val STREET_STORE = "street"
            const val NUMBER_STORE = "number"
            const val BAIRRO_STORE = "bairro"
            const val CITY_STORE = "city"
            const val UF_STORE = "uf"
            const val TIME = "timeZone"
        }
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createStore = "CREATE TABLE ${FeedReaderContract.FeedEntry.TABLE_STORE} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${FeedReaderContract.FeedEntry.NAME_STORE} TEXT, " +
                "${FeedReaderContract.FeedEntry.PASSOWORD_STORE} TEXT, " +
                "${FeedReaderContract.FeedEntry.CNPJ_STORE} TEXT, " +
                "${FeedReaderContract.FeedEntry.STREET_STORE} TEXT, " +
                "${FeedReaderContract.FeedEntry.NUMBER_STORE} TEXT, " +
                "${FeedReaderContract.FeedEntry.BAIRRO_STORE} TEXT, " +
                "${FeedReaderContract.FeedEntry.CITY_STORE} TEXT, " +
                "${FeedReaderContract.FeedEntry.UF_STORE} TEXT, " +
                "${FeedReaderContract.FeedEntry.TIME} TEXT )"

        db!!.execSQL(createStore)

        db!!.execSQL("INSERT INTO ${FeedReaderContract.FeedEntry.TABLE_STORE} " +
                "(name, password, cnpj, street, number, bairro, city, uf, timeZone) VALUES" +
                "('Mercado', 'teste', '1', 'rua de teste', '1', 'bairro de teste', 'cidade de teste', 'RJ', '19:00')")

        val createProduct = "CREATE TABLE ${FeedReaderContract.FeedEntry.TABLE_PRODUCTS} (" +
                "${BaseColumns._ID} INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "${FeedReaderContract.FeedEntry.NAME_PRODUCTS} TEXT, " +
                "${FeedReaderContract.FeedEntry.CATEGORY_PRODUCTS} TEXT, " +
                "${FeedReaderContract.FeedEntry.PRICE_PRODUCTS} TEXT, " +
                "${FeedReaderContract.FeedEntry.QT_PRODUCTS} INT, " +
                "${FeedReaderContract.FeedEntry.UNITY_PRODUCTS} TEXT, " +
                "${FeedReaderContract.FeedEntry.ID_STORE} INT NOT NULL CONSTRAINT store_id REFERENCES stores (id) )"

        db!!.execSQL(createProduct)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.TABLE_STORE}")
        db!!.execSQL("DROP TABLE IF EXISTS ${FeedReaderContract.FeedEntry.TABLE_PRODUCTS}")

        onCreate(db)
    }

    companion object
    {
        const val DATABASE_NAME = "OnPriceApp.db"
        const val DATABASE_VERSION = 1
    }
}