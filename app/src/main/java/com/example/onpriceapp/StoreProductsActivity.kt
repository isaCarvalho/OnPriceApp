package com.example.onpriceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onpriceapp.model.Product
import com.example.onpriceapp.model.Store
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await
import java.lang.Exception

const val EXTRA = "com.example.onpriceapp.MESSAGE"

class StoreProductsActivity : AppCompatActivity() {

    var fab : FloatingActionButton? = null
    private var id : Int = 0
    private var store : Store? = null

    var viewManager : LinearLayoutManager? = null
    private lateinit var viewAdapter: ProductStoreAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_products)

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.getStore(id).await()
            store = response
        }

        val products = ArrayList<Product>()

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.listProducts(ID_STORE).await()

            for (product in response)
                products.add(product)
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = ProductStoreAdapter(products, ID_STORE)

        recyclerView = findViewById<RecyclerView>(R.id.productsStoreList).apply {
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = viewAdapter
        }

        fab = findViewById(R.id.fabInsert)

        fab!!.setOnClickListener {
            val intent = Intent(this, CreateProductActivity::class.java).apply {
                putExtra(EXTRA, id)
            }
            startActivity(intent)
        }
    }

    override fun onResume() {
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
                    store!!.street, store!!.number.toString(), store!!.bairro, store!!.city, store!!.time)

                val intent = Intent(this, CreateAccountActivity::class.java).apply {
                    putExtra(EXTRA, array)
                }

                startActivity(intent)
                true
            }

            R.id.delete ->
            {
                try
                {
                    GlobalScope.launch(Dispatchers.IO) {
                        api.deleteStore(id)
                    }

                    Toast.makeText(this, "Conta deletada!", Toast.LENGTH_SHORT).show()

                    finish()
                } catch (e: Exception) {
                    Toast.makeText(this, "Não foi possível deletar a conta!", Toast.LENGTH_SHORT)
                        .show()
                }
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
}
