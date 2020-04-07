package com.example.onpriceapp.controller

import android.content.Context
import com.example.onpriceapp.database.DatabaseController
import com.example.onpriceapp.model.Product
import java.lang.Exception

class ProductController(context : Context)
{
    var dbController = DatabaseController(context)

    fun insert(name : String, category : String, price : String, stamp : String,
                      quantity : Int, unity : String, store_id : Int) : Boolean
    {
        return try
        {
            dbController.insertProduct(name, category, price, stamp, quantity, unity, store_id)
            true
        } catch (e : Exception)
        {
            false
        }
    }

    fun list() : Array<Product>
    {
        return dbController.listProducts().toTypedArray()
    }

    fun delete(id : Int)
    {

    }
}