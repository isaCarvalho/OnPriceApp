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

    fun update(id : Int, name : String, category : String, price : String, stamp : String,
               quantity : Int, unity : String) : Boolean
    {
        return try {
            dbController.updateProduct(id, name, category, price, stamp, quantity, unity)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun list(id_store : Int) : ArrayList<Product>
    {
        return dbController.listProducts(id_store) as ArrayList<Product>
    }

    fun delete(id : Int) : Boolean
    {
        return try
        {
            dbController.deleteProduct(id)
            true
        } catch (e : Exception)
        {
            false
        }
    }
}