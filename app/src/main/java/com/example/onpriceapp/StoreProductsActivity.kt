package com.example.onpriceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onpriceapp.adapter.ProductStoreAdapter
import com.example.onpriceapp.model.Product
import com.example.onpriceapp.model.Store
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoreProductsActivity : AppCompatActivity() {

    var fab : FloatingActionButton? = null
    private var store : Store? = null

    var viewManager : LinearLayoutManager? = null
    private lateinit var viewAdapter: ProductStoreAdapter
    private lateinit var recyclerView: RecyclerView

    init {
        api.getStore(ID_STORE).enqueue(object : Callback<List<Store>> {
            override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                Log.e(ERROR, call.toString())
            }

            override fun onResponse(call: Call<List<Store>>, response: Response<List<Store>>) {
                if (response.body()!!.isNotEmpty())
                {
                    store = response.body()!![0]
                    Log.d("STORE: ", store.toString())
                }
                else
                    finish()
            }
        })

    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_products)

        viewManager = LinearLayoutManager(this)

        recyclerView = findViewById<RecyclerView>(R.id.productsStoreList).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
        }

        getProducts(ID_STORE)

        fab = findViewById(R.id.fabInsert)

        fab!!.setOnClickListener {
            val intent = Intent(this, CreateProductActivity::class.java).apply {
                putExtra(EXTRA, ID_STORE)
                putExtra(EXTRA, emptyArray<String>())
            }
            startActivity(intent)
        }
    }

    override fun onResume()
    {
        getProducts(ID_STORE)
        super.onResume()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)

        val searchItem = menu!!.findItem(R.id.app_bar_search_store)
        val searchView : SearchView = searchItem.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewAdapter.filter.filter(newText)
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId)
        {
            R.id.edit -> {
                val array = arrayOf(store!!.id.toString(), store!!.name, store!!.password, store!!.cnpj,
                    store!!.street, store!!.number, store!!.bairro, store!!.city, store!!.time)

                val intent = Intent(this, CreateAccountActivity::class.java).apply {
                    putExtra(EXTRA, array)
                }

                startActivity(intent)
                true
            }

            R.id.delete ->
            {
                api.deleteStore(store!!.id).enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Log.e(ERROR, call.toString())

                        Toast.makeText(this@StoreProductsActivity, "Não foi possível deletar a conta!",
                            Toast.LENGTH_SHORT).show()

                        ID_STORE = 0
                        SESSION_LOGIN = false

                        finish()
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        Toast.makeText(this@StoreProductsActivity, "Conta deletada!", Toast.LENGTH_SHORT)
                            .show()

                        ID_STORE = 0
                        SESSION_LOGIN = false

                        finish()
                    }
                })
                true
            }

            R.id.logout ->
            {
                SESSION_LOGIN = false
                ID_STORE = -1

                finish()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getProducts(id_store: Int)
    {
        val textView = findViewById<TextView>(R.id.noProducts2)

        api.listProducts(id_store).enqueue(object: Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                textView.setText(R.string.error)
            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                val products = ArrayList<Product>()

                response.body()!!.forEach {product ->
                    products.add(product)
                }

                if (products.isEmpty())
                    textView.setText(R.string.noproducts2)
                else
                    textView.text = null

                viewAdapter = ProductStoreAdapter(products)
                recyclerView.adapter = viewAdapter
            }
        })
    }
}
