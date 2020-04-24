package com.example.onpriceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onpriceapp.api.APIController
import com.example.onpriceapp.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await

class ProductsActivity : AppCompatActivity() {

    var viewManager : LinearLayoutManager? = null
    private lateinit var viewAdapter: ListProductsAdapter
    private lateinit var recyclerView: RecyclerView

    var id_store : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_products)
        super.onCreate(savedInstanceState)

        id_store = intent.getIntExtra(EXTRA, -1)

        val products = ArrayList<Product>()

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.listProducts(id_store).await()

            for (product in response)
                products.add(product)
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = ListProductsAdapter(products)

        recyclerView = findViewById<RecyclerView>(R.id.productsListRecycler).apply {
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu!!.findItem(R.id.app_bar_search_generic)
        val searchView = searchItem.actionView as SearchView

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
}
