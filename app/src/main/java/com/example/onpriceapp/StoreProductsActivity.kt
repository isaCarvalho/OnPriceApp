package com.example.onpriceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton

const val EXTRA = "com.example.onpriceapp.MESSAGE"

class StoreProductsActivity : AppCompatActivity() {

    var fab : FloatingActionButton? = null
    private var id : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_products)

        id = intent.getIntExtra(EXTRA, -1)

        fab = findViewById(R.id.fabInsert)

        fab!!.setOnClickListener {
            val intent = Intent(this, CreateProductActivity::class.java).apply {
                putExtra(EXTRA, id)
            }
            startActivity(intent)
        }
    }
}
