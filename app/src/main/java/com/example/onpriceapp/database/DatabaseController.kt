package com.example.onpriceapp.database

import android.content.ContentValues
import android.content.Context
import android.provider.BaseColumns
import androidx.core.content.contentValuesOf
import com.example.onpriceapp.model.Product
import com.example.onpriceapp.model.Store

class DatabaseController(private val context : Context)
{
    private  val dbHelper = DatabaseCreate(context)

    fun insertStore(name : String, password : String, cnpj : String,
        street : String, number : String, bairro : String, city : String,
        uf : String, time : String)
    {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseCreate.FeedReaderContract.FeedEntry.NAME_STORE, name)
            put(DatabaseCreate.FeedReaderContract.FeedEntry.PASSOWORD_STORE, password)
            put(DatabaseCreate.FeedReaderContract.FeedEntry.CNPJ_STORE, cnpj)
            put(DatabaseCreate.FeedReaderContract.FeedEntry.STREET_STORE, street)
            put(DatabaseCreate.FeedReaderContract.FeedEntry.NUMBER_STORE, number)
            put(DatabaseCreate.FeedReaderContract.FeedEntry.CITY_STORE, city)
            put(DatabaseCreate.FeedReaderContract.FeedEntry.BAIRRO_STORE, bairro)
            put(DatabaseCreate.FeedReaderContract.FeedEntry.UF_STORE, uf)
            put(DatabaseCreate.FeedReaderContract.FeedEntry.TIME, time)
        }

        db?.insert(DatabaseCreate.FeedReaderContract.FeedEntry.TABLE_STORE, null, values)
    }

    fun insertProduct(name : String, category : String, price : String, stamp : String,
                    quantity : Int, unity : String, store_id : Int)
    {
        val db = dbHelper.writableDatabase

        val values = ContentValues().apply {
            put(DatabaseCreate.FeedReaderContract.FeedEntry.NAME_PRODUCTS, name)
            put(DatabaseCreate.FeedReaderContract.FeedEntry.CATEGORY_PRODUCTS, category)
            put(DatabaseCreate.FeedReaderContract.FeedEntry.PRICE_PRODUCTS, price)
            put(DatabaseCreate.FeedReaderContract.FeedEntry.QT_PRODUCTS, quantity)
            put(DatabaseCreate.FeedReaderContract.FeedEntry.STAMP_PRODUCTS, stamp)
            put(DatabaseCreate.FeedReaderContract.FeedEntry.UNITY_PRODUCTS, unity)
            put(DatabaseCreate.FeedReaderContract.FeedEntry.ID_STORE, store_id)
        }

        db?.insert(DatabaseCreate.FeedReaderContract.FeedEntry.TABLE_PRODUCTS, null, values)
    }

    fun listStores() : MutableList<Store>
    {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(BaseColumns._ID, DatabaseCreate.FeedReaderContract.FeedEntry.NAME_STORE,
            DatabaseCreate.FeedReaderContract.FeedEntry.STREET_STORE, DatabaseCreate.FeedReaderContract.FeedEntry.NUMBER_STORE,
            DatabaseCreate.FeedReaderContract.FeedEntry.UF_STORE, DatabaseCreate.FeedReaderContract.FeedEntry.CNPJ_STORE,
            DatabaseCreate.FeedReaderContract.FeedEntry.CITY_STORE, DatabaseCreate.FeedReaderContract.FeedEntry.PASSOWORD_STORE,
            DatabaseCreate.FeedReaderContract.FeedEntry.BAIRRO_STORE, DatabaseCreate.FeedReaderContract.FeedEntry.TIME)

        val cursor = db.query(
            DatabaseCreate.FeedReaderContract.FeedEntry.TABLE_STORE,
            projection,
            null,
            null,
            null,
            null,
            null
        )

        val items = mutableListOf<Store>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val name = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.NAME_STORE))
                val password = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.PASSOWORD_STORE))
                val cnpj = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.CNPJ_STORE))
                val street = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.STREET_STORE))
                val number = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.NUMBER_STORE)).toInt()
                val bairro = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.BAIRRO_STORE))
                val city = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.CITY_STORE))
                val time = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.TIME))
                val uf = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.UF_STORE))

                val store = Store(id, name, password, cnpj, street, number, bairro, city, uf, time)

                items.add(store)
            }
        }

        cursor.close()

        return items
    }

    fun listProducts(id_store : Int) : MutableList<Product>
    {
        val db = dbHelper.readableDatabase

        val projection = arrayOf(BaseColumns._ID, DatabaseCreate.FeedReaderContract.FeedEntry.NAME_PRODUCTS,
            DatabaseCreate.FeedReaderContract.FeedEntry.CATEGORY_PRODUCTS, DatabaseCreate.FeedReaderContract.FeedEntry.UNITY_PRODUCTS,
            DatabaseCreate.FeedReaderContract.FeedEntry.QT_PRODUCTS, DatabaseCreate.FeedReaderContract.FeedEntry.STAMP_PRODUCTS, DatabaseCreate.FeedReaderContract.FeedEntry.PRICE_PRODUCTS,
            DatabaseCreate.FeedReaderContract.FeedEntry.ID_STORE)

        val cursor = db.query(
            DatabaseCreate.FeedReaderContract.FeedEntry.TABLE_PRODUCTS,
            projection,
            "${DatabaseCreate.FeedReaderContract.FeedEntry.ID_STORE} = ?",
            arrayOf(id_store.toString()),
            null,
            null,
            null
        )

        val items = mutableListOf<Product>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val name = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.NAME_PRODUCTS))
                val category = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.CATEGORY_PRODUCTS))
                val price = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.PRICE_PRODUCTS))
                val qt = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.QT_PRODUCTS)).toInt()
                val unity = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.UNITY_PRODUCTS))
                val stamp = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.STAMP_PRODUCTS))

                val product = Product(id, name, category, price, stamp, qt, unity)

                items.add(product)
            }
        }

        cursor.close()

        return items
    }

    fun login(name : String, password : String) : Int
    {
        val items = dbHelper.readableDatabase.query(
            DatabaseCreate.FeedReaderContract.FeedEntry.TABLE_STORE,
            arrayOf(BaseColumns._ID, "name", "password"),
            "name = ? AND password = ?",
            arrayOf(name, password),
            null,
            null,
            null
        )

        var id = -1
        with(items) {
            while (this.moveToNext())
                id = getInt(getColumnIndexOrThrow(BaseColumns._ID))
        }

        return id
    }

    fun getStore(id : Int) : Store?
    {
        val projection = arrayOf(BaseColumns._ID, DatabaseCreate.FeedReaderContract.FeedEntry.NAME_STORE,
            DatabaseCreate.FeedReaderContract.FeedEntry.STREET_STORE, DatabaseCreate.FeedReaderContract.FeedEntry.NUMBER_STORE,
            DatabaseCreate.FeedReaderContract.FeedEntry.UF_STORE, DatabaseCreate.FeedReaderContract.FeedEntry.CNPJ_STORE,
            DatabaseCreate.FeedReaderContract.FeedEntry.CITY_STORE, DatabaseCreate.FeedReaderContract.FeedEntry.PASSOWORD_STORE,
            DatabaseCreate.FeedReaderContract.FeedEntry.BAIRRO_STORE, DatabaseCreate.FeedReaderContract.FeedEntry.TIME)

        val items = dbHelper.readableDatabase.query(
            DatabaseCreate.FeedReaderContract.FeedEntry.TABLE_STORE,
            projection,
            "${BaseColumns._ID} = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        with(items) {
            while (this.moveToNext()) {
                val id_store = getInt(getColumnIndexOrThrow(BaseColumns._ID))
                val name = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.NAME_STORE))
                val password = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.PASSOWORD_STORE))
                val cnpj = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.CNPJ_STORE))
                val street = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.STREET_STORE))
                val number = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.NUMBER_STORE)).toInt()
                val neightborhood = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.BAIRRO_STORE))
                val city = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.CITY_STORE))
                val uf = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.UF_STORE))
                val timeZone = getString(getColumnIndexOrThrow(DatabaseCreate.FeedReaderContract.FeedEntry.TIME))

                return Store(id_store, name, password, cnpj, street, number, neightborhood, city, uf, timeZone)
            }
        }

        return null
    }

    fun updateStore(id : Int, name : String, password : String, cnpj : String,
                    street : String, number : String, bairro : String, city : String,
                    uf : String, time : String)
    {
        val db = dbHelper.writableDatabase

        val contents = contentValuesOf(
            Pair(DatabaseCreate.FeedReaderContract.FeedEntry.NAME_STORE, name),
            Pair(DatabaseCreate.FeedReaderContract.FeedEntry.PASSOWORD_STORE, password),
            Pair(DatabaseCreate.FeedReaderContract.FeedEntry.CNPJ_STORE, cnpj),
            Pair(DatabaseCreate.FeedReaderContract.FeedEntry.STREET_STORE, street),
            Pair(DatabaseCreate.FeedReaderContract.FeedEntry.NUMBER_STORE, number),
            Pair(DatabaseCreate.FeedReaderContract.FeedEntry.BAIRRO_STORE, bairro),
            Pair(DatabaseCreate.FeedReaderContract.FeedEntry.CITY_STORE, city),
            Pair(DatabaseCreate.FeedReaderContract.FeedEntry.UF_STORE, uf),
            Pair(DatabaseCreate.FeedReaderContract.FeedEntry.TIME, time)
        )

        db.update(DatabaseCreate.FeedReaderContract.FeedEntry.TABLE_STORE,contents, "${BaseColumns._ID} = ?", arrayOf(id.toString()))
    }

    fun updateProduct(id : Int, name : String, category : String, price : String, stamp : String,
                      quantity : Int, unity : String)
    {
        val db = dbHelper.writableDatabase

        val contents = contentValuesOf(
            Pair(DatabaseCreate.FeedReaderContract.FeedEntry.NAME_PRODUCTS, name),
            Pair(DatabaseCreate.FeedReaderContract.FeedEntry.CATEGORY_PRODUCTS, category),
            Pair(DatabaseCreate.FeedReaderContract.FeedEntry.PRICE_PRODUCTS, price),
            Pair(DatabaseCreate.FeedReaderContract.FeedEntry.STAMP_PRODUCTS, stamp),
            Pair(DatabaseCreate.FeedReaderContract.FeedEntry.QT_PRODUCTS, quantity),
            Pair(DatabaseCreate.FeedReaderContract.FeedEntry.UNITY_PRODUCTS, unity)
        )

        db.update(DatabaseCreate.FeedReaderContract.FeedEntry.TABLE_PRODUCTS, contents, "${BaseColumns._ID} = ?", arrayOf(id.toString()))
    }

    fun deleteStore(id : Int)
    {
        dbHelper.writableDatabase.delete(DatabaseCreate.FeedReaderContract.FeedEntry.TABLE_STORE,
                "${BaseColumns._ID} = ?", arrayOf(id.toString())
        )
    }

    fun deleteProduct(id : Int)
    {
        dbHelper.writableDatabase.delete(DatabaseCreate.FeedReaderContract.FeedEntry.TABLE_PRODUCTS,
            "${BaseColumns._ID} = ?", arrayOf(id.toString()))
    }
}