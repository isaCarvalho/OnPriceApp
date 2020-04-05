package com.example.onpriceapp.controller

import android.content.Context
import com.example.onpriceapp.database.DatabaseController
import java.lang.Exception

class ProductController(context : Context)
{
    var dbController = DatabaseController(context)

    fun insert(name : String, category : String, price : String,
                      quantity : Int, unity : String, store_id : Int) : Boolean
    {
        return try
        {
            dbController.insertProduct(name, category, price, quantity, unity, store_id)
            true
        } catch (e : Exception)
        {
            false
        }
    }
}