package com.example.onpriceapp.api

import android.content.Context
import android.util.Log
import com.android.volley.Request
import java.net.*
import com.example.onpriceapp.model.Product
import com.example.onpriceapp.model.Store
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.google.gson.JsonArray
import org.json.JSONObject

class APIController(private val context: Context)
{
    private val host = "https://onpriceapi.herokuapp.com"

    fun insertStore(name : String, password : String, cnpj : String,
        street : String, number : String, bairro : String, city : String,
        uf : String, time : String)
    {
        val jsonObject = JSONObject()
        jsonObject.put("name", name)
        jsonObject.put("password", password)
        jsonObject.put("cnpj", cnpj)
        jsonObject.put("street", street)
        jsonObject.put("number", number)
        jsonObject.put("bairro", bairro)
        jsonObject.put("city", city)
        jsonObject.put("uf", uf)
        jsonObject.put("time", time)


        val requestQueue = Volley.newRequestQueue(context)

        val requestObject = JsonObjectRequest(
            Request.Method.POST,
            "$host/stores",
            jsonObject,
            Response.Listener { response ->
                Log.e("Login response", response.toString())
            },
            Response.ErrorListener {error ->
                Log.e("Error response", error.toString())
            }
        )

        requestQueue.add(requestObject)
    }

    fun insertProduct(name : String, category : String, price : String, stamp : String,
                    quantity : Int, unity : String, store_id : Int)
    {
        val jsonObject = JSONObject()
        jsonObject.put("name", name)
        jsonObject.put("category", category)
        jsonObject.put("price", price)
        jsonObject.put("stamp", stamp)
        jsonObject.put("quantity", quantity)
        jsonObject.put("unity", unity)
        jsonObject.put("id_store", store_id)


        val requestQueue = Volley.newRequestQueue(context)

        val requestObject = JsonObjectRequest(
            Request.Method.POST,
            "$host/products",
            jsonObject,
            Response.Listener { response ->
                Log.e("Login response", response.toString())
            },
            Response.ErrorListener {error ->
                Log.e("Error response", error.toString())
            }
        )

        requestQueue.add(requestObject)
    }

    fun listStores() : MutableList<Store>
    {
        val stores = ArrayList<Store>()
        val requestQueue = Volley.newRequestQueue(context)

        val requestObject = JsonArrayRequest(
            Request.Method.GET,
            "$host/stores",
            null,
            Response.Listener { response ->
                Log.e("Login response", response.toString())

                if (response.length() != 0)
                {
                    val array = response.getJSONArray(0)
                    for (i in 0 until array.length()) {

                        val store = Store(
                            array.getInt(0),
                            array.getString(1),
                            array.getString(2),
                            array.getString(3),
                            array.getString(4),
                            array.getInt(5),
                            array.getString(6),
                            array.getString(7),
                            array.getString(8),
                            array.getString(9)
                        )

                        stores.add(store)
                    }
                }
            },
            Response.ErrorListener {error ->
                Log.e("Error response", error.toString())
            }
        )

        requestQueue.add(requestObject)


        return stores
    }

    fun listProducts(id_store : Int) : MutableList<Product>
    {
        val products = ArrayList<Product>()
        val requestQueue = Volley.newRequestQueue(context)

        val requestObject = JsonArrayRequest(
            Request.Method.GET,
            "$host/products",
            null,
            Response.Listener { response ->
                Log.e("Login response", response.toString())

                if (response.length() != 0)
                {
                    val array = response.getJSONArray(0)
                    for (i in 0 until array.length()) {

                        val store = Product(
                            array.getInt(0),
                            array.getString(1),
                            array.getString(2),
                            array.getString(3),
                            array.getString(4),
                            array.getInt(5),
                            array.getString(6)
                        )

                        products.add(store)
                    }
                }
            },
            Response.ErrorListener {error ->
                Log.e("Error response", error.toString())
            }
        )

        requestQueue.add(requestObject)


        return products
    }

    fun login(name : String, password : String) : Int
    {
        var id = -1

        val requestQueue = Volley.newRequestQueue(context)

        val requestObject = JsonArrayRequest(
            Request.Method.GET,
            "$host/login?name=$name&password=$password",
            null,
            Response.Listener { response ->
                Log.e("Login response", response.toString())

                if (response.length() != 0)
                {
                    val array = response.getJSONArray(0)
                    if (array.length() != 0) {
                        id = array.getInt(0)
                        Log.e("id", id.toString())
                    }
                }
            },
            Response.ErrorListener {error ->
                Log.e("Error response", error.toString())
            }
        )

        requestQueue.add(requestObject)

        return id
    }

    fun getStore(id : Int) : Store?
    {
        var store : Store? = null
        val requestQueue = Volley.newRequestQueue(context)

        val requestObject = JsonArrayRequest(
            Request.Method.GET,
            "$host/stores?id=$id",
            null,
            Response.Listener { response ->
                Log.e("Login response", response.toString())

                if (response.length() != 0)
                {
                    val array = response.getJSONArray(0)
                    if (array.length() != 0) {
                        store = Store(
                            array.getInt(0),
                            array.getString(1),
                            array.getString(2),
                            array.getString(3),
                            array.getString(4),
                            array.getInt(5),
                            array.getString(6),
                            array.getString(7),
                            array.getString(8),
                            array.getString(9)
                        )

                        Log.e("id", id.toString())
                    }
                }
            },
            Response.ErrorListener {error ->
                Log.e("Error response", error.toString())
            }
        )

        requestQueue.add(requestObject)


        return store
    }

    fun updateStore(id : Int, name : String, password : String, cnpj : String,
                    street : String, number : String, bairro : String, city : String,
                    uf : String, time : String)
    {
        val jsonObject = JSONObject()
        jsonObject.put("name", name)
        jsonObject.put("password", password)
        jsonObject.put("cnpj", cnpj)
        jsonObject.put("street", street)
        jsonObject.put("number", number)
        jsonObject.put("bairro", bairro)
        jsonObject.put("city", city)
        jsonObject.put("uf", uf)
        jsonObject.put("time", time)

        val requestQueue = Volley.newRequestQueue(context)

        val requestObject = JsonObjectRequest(
            Request.Method.PUT,
            "$host/stores?id=$id",
            null,
            Response.Listener { response ->
                Log.e("Login response", response.toString())
            },
            Response.ErrorListener {error ->
                Log.e("Error response", error.toString())
            }
        )

        requestQueue.add(requestObject)
    }

    fun updateProduct(id : Int, name : String, category : String, price : String, stamp : String,
                      quantity : Int, unity : String)
    {
        val jsonObject = JSONObject()
        jsonObject.put("name", name)
        jsonObject.put("category", category)
        jsonObject.put("price", price)
        jsonObject.put("stamp", stamp)
        jsonObject.put("quantity", quantity)
        jsonObject.put("unity", unity)

        val requestQueue = Volley.newRequestQueue(context)

        val requestObject = JsonObjectRequest(
            Request.Method.PUT,
            "$host/products?id=$id",
            jsonObject,
            Response.Listener { response ->
                Log.e("Login response", response.toString())
            },
            Response.ErrorListener {error ->
                Log.e("Error response", error.toString())
            }
        )

        requestQueue.add(requestObject)
    }

    fun deleteStore(id : Int)
    {
        val requestQueue = Volley.newRequestQueue(context)

        val requestObject = JsonObjectRequest(
            Request.Method.DELETE,
            "$host/stores?id=$id",
            null,
            Response.Listener { response ->
                Log.e("Login response", response.toString())
            },
            Response.ErrorListener {error ->
                Log.e("Error response", error.toString())
            }
        )

        requestQueue.add(requestObject)
    }

    fun deleteProduct(id : Int)
    {
        val requestQueue = Volley.newRequestQueue(context)

        val requestObject = JsonObjectRequest(
            Request.Method.DELETE,
            "$host/products?id=$id",
            null,
            Response.Listener { response ->
                Log.e("Login response", response.toString())
            },
            Response.ErrorListener {error ->
                Log.e("Error response", error.toString())
            }
        )

        requestQueue.add(requestObject)
    }
}