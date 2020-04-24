package com.example.onpriceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import com.example.onpriceapp.api.API
import com.example.onpriceapp.api.APIController


val api = APIController.getRetrofitInstance().create(API::class.java)

var SESSION_LOGIN = false
var ID_STORE = 0

class MainActivity : AppCompatActivity() {

    var cardStore : ImageView? = null
    var cardProduct : ImageView? = null
    var openButton : Button? = null
    var createAccountButton : Button? = null
    var loginMain : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardStore = findViewById(R.id.cardStoreImage)

        cardStore!!.setOnClickListener {
            openLogin()
        }

        createAccountButton = findViewById(R.id.createAccountButton)
        createAccountButton!!.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java).apply {
                putExtra(EXTRA, arrayOf<String>())
            }
            startActivity(intent)
        }

        loginMain = findViewById(R.id.loginMain)
        loginMain!!.setOnClickListener {
            openLogin()
        }

        cardProduct = findViewById(R.id.cardProductImage)

        cardProduct!!.setOnClickListener {
            startActivity(Intent(this, ListStoresActivity::class.java))
        }

        openButton = findViewById(R.id.search)

        openButton!!.setOnClickListener {
            startActivity(Intent(this, ListStoresActivity::class.java))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId)
        {
            R.id.main_menu_help -> {
                startActivity(Intent(this, HelpActivity::class.java))

                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun openLogin()
    {
        if (SESSION_LOGIN)
        {
            val intent = Intent(this, StoreProductsActivity::class.java).apply {
                putExtra(EXTRA, ID_STORE)
            }
            startActivity(intent)
        }
        else
        {
            val intent = Intent(this, StoreLoginActivity::class.java)
            startActivity(intent)
        }
    }
}
