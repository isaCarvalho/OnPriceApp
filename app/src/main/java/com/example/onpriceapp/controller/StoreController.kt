package com.example.onpriceapp.controller

import android.content.Context
import com.example.onpriceapp.database.DatabaseController
import com.example.onpriceapp.model.Store
import java.lang.Exception

class StoreController(context: Context) {

    val dbController = DatabaseController(context)

    fun login(name : String, password: String) : Int
    {
        return dbController.login(name, password)
    }

    fun createAccount(name : String, password : String, cnpj : String,
          street : String, number : String, bairro : String, city : String,
          uf : String, time : String) : Boolean
    {
        return try {
            dbController.insertStore(name, password, cnpj, street, number, bairro, city, uf, time)

            true
        } catch (e : Exception) {
            false
        }
    }

    fun update(id : Int, name : String, password : String, cnpj : String,
               street : String, number : String, bairro : String, city : String,
               uf : String, time : String) : Boolean
    {
        return try {
            dbController.updateStore(id, name, password, cnpj, street, number, bairro, city, uf, time)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun list() : ArrayList<Store>
    {
        return dbController.listStores() as ArrayList<Store>
    }

    fun get(id: Int) : Store?
    {
        return dbController.getStore(id)
    }

    fun delete(id: Int) : Boolean
    {
        return try {
            dbController.deleteStore(id)
            true
        }
        catch (e : Exception)
        {
            false
        }
    }
}