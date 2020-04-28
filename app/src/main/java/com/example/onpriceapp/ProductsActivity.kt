package com.example.onpriceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onpriceapp.adapter.ListProductsAdapter
import com.example.onpriceapp.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductsActivity : AppCompatActivity() {

    var viewManager : LinearLayoutManager? = null
    private lateinit var viewAdapter: ListProductsAdapter
    private lateinit var recyclerView: RecyclerView

    private var idStore : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_products)
        super.onCreate(savedInstanceState)

        idStore = intent.getIntExtra(EXTRA, -1)

        viewManager = LinearLayoutManager(this)

        recyclerView = findViewById<RecyclerView>(R.id.productsListRecycler).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
        }

        getProducts(idStore)
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

    private fun getProducts(id_store: Int)
    {
        val textView = findViewById<TextView>(R.id.noProducts)

        api.listProducts(id_store).enqueue(object: Callback<List<Product>> {
            override fun onFailure(call: Call<List<Product>>, t: Throwable) {
                textView.setText(R.string.error)
            }

            override fun onResponse(call: Call<List<Product>>, response: Response<List<Product>>) {
                val products = ArrayList<Product>()

                response.body()!!.forEach { product ->
                    products.add(product)
                }

                if (products.isEmpty())
                    textView.setText(R.string.noproducts)
                else
                    textView.text = null

                viewAdapter = ListProductsAdapter(products)
                recyclerView.adapter = viewAdapter
            }
        })
    }
}
