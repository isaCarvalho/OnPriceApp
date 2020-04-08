package com.example.onpriceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onpriceapp.controller.StoreController

class ListStoresActivity : AppCompatActivity() {

    private lateinit var viewManager : LinearLayoutManager
    private lateinit var viewAdapter : StoreAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_stores)

        viewManager = LinearLayoutManager(this)
        viewAdapter = StoreAdapter(StoreController(this).list())

        recyclerView = findViewById<RecyclerView>(R.id.storeList).apply {
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = viewAdapter
        }
    }
}
