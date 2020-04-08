package com.example.onpriceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onpriceapp.controller.ProductController

class ProductsActivity : AppCompatActivity() {

    var viewManager : LinearLayoutManager? = null
    private lateinit var viewAdapter: ListProductsAdapter
    private lateinit var recyclerView: RecyclerView

    var id_store : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_products)
        super.onCreate(savedInstanceState)

        id_store = intent.getIntExtra(EXTRA, -1)

        viewManager = LinearLayoutManager(this)
        viewAdapter = ListProductsAdapter(ProductController(this).list(id_store))

        recyclerView = findViewById<RecyclerView>(R.id.productsListRecycler).apply {
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
