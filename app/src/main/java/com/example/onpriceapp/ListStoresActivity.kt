package com.example.onpriceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onpriceapp.model.Store
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await

class ListStoresActivity : AppCompatActivity() {

    private lateinit var viewManager : LinearLayoutManager
    private lateinit var viewAdapter : StoreAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_stores)

        val stores = ArrayList<Store>()

        GlobalScope.launch(Dispatchers.IO) {
            val response = api.listStores().await()

            for (store in response)
                stores.add(store)
        }

        viewManager = LinearLayoutManager(this)
        viewAdapter = StoreAdapter(stores)

        recyclerView = findViewById<RecyclerView>(R.id.storeList).apply {
            setHasFixedSize(true)

            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchItem = menu!!.findItem(R.id.app_bar_search_generic)
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
}
