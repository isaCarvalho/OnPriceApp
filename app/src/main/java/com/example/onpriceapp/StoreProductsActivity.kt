package com.example.onpriceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.example.onpriceapp.controller.StoreController
import com.example.onpriceapp.model.Store
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val EXTRA = "com.example.onpriceapp.MESSAGE"

class StoreProductsActivity : AppCompatActivity() {

    var fab : FloatingActionButton? = null
    private var id : Int = 0
    private var store : Store? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_products)

        id = intent.getIntExtra(EXTRA, -1)
        store = StoreController(this).get(id)

        fab = findViewById(R.id.fabInsert)

        fab!!.setOnClickListener {
            val intent = Intent(this, CreateProductActivity::class.java).apply {
                putExtra(EXTRA, id)
            }
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.edit_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId)
        {
            R.id.edit -> {
                val intent = Intent(this, CreateAccountActivity::class.java).apply {
                    putExtra(EXTRA, arrayOf(store!!.id.toString(), store!!.name, store!!.password, store!!.cnpj,
                        store!!.street, store!!.number, store!!.neightborhood, store!!.city, store!!.timeZone))
                }

                startActivity(intent)
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}
