package com.example.onpriceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.SearchView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.onpriceapp.adapter.StoreAdapter
import com.example.onpriceapp.model.Store
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListStoresActivity : AppCompatActivity() {

    private lateinit var viewManager : LinearLayoutManager
    private lateinit var viewAdapter : StoreAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_stores)

        viewManager = LinearLayoutManager(this)

        recyclerView = findViewById<RecyclerView>(R.id.storeList).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
        }

        getStores()
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

    private fun getStores()
    {
        val textView = findViewById<TextView>(R.id.noStores)

        api.listStores().enqueue(object : Callback<List<Store>> {
            override fun onFailure(call: Call<List<Store>>, t: Throwable) {
                textView.setText(R.string.error)
            }

            override fun onResponse(call: Call<List<Store>>, response: Response<List<Store>>) {
                val stores = ArrayList<Store>()

                response.body()!!.forEach { store ->
                    stores.add(store)
                }

                if (stores.isEmpty())
                    textView.setText(R.string.nostore)

                viewAdapter = StoreAdapter(stores)
                recyclerView.adapter = viewAdapter
            }
        })

    }
}
