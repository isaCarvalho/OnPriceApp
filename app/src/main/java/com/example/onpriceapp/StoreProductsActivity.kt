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
import com.example.onpriceapp.controller.ProductController
import com.example.onpriceapp.controller.StoreController
import com.example.onpriceapp.model.Store
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val EXTRA = "com.example.onpriceapp.MESSAGE"

class StoreProductsActivity : AppCompatActivity() {

    var fab : FloatingActionButton? = null
    private var id : Int = 0
    private var store : Store? = null

    var viewManager : LinearLayoutManager? = null
    private lateinit var viewAdapter: ProductStoreAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_products)

        id = intent.getIntExtra(EXTRA, -1)
        store = StoreController(this).get(id)

        viewManager = LinearLayoutManager(this)
        viewAdapter = ProductStoreAdapter(ProductController(this).list(id), id)

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
                    store!!.street, store!!.number.toString(), store!!.neightborhood, store!!.city, store!!.timeZone)

                val intent = Intent(this, CreateAccountActivity::class.java).apply {
                    putExtra(EXTRA, array)
                }

                startActivity(intent)
                true
            }

            R.id.delete ->
            {
                if (StoreController(this).delete(id)) {
                    Toast.makeText(this, "Conta deletada com sucessso!", Toast.LENGTH_SHORT).show()

                    startActivity(Intent(this, StoreLoginActivity::class.java))
                }
                else
                    Toast.makeText(this, "Não foi possível deletar a conta!", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
