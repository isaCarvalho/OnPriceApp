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

const val EXTRA = "com.example.onpriceapp.MESSAGE" // extra message tag
const val ERROR = "Error: " // error message tag
const val DEFAULT_INT_VALUE = -1 // default value for id

val api = APIController.getRetrofitInstance().create(API::class.java) // instance of retrofit

var SESSION_LOGIN = false // session variable
var ID_STORE = 0 // id variable. It's used for login

/**
 * This is the main activity for onPrice.
 */
class MainActivity : AppCompatActivity() {

    var cardStore : ImageView? = null // Store's image
    var cardProduct : ImageView? = null // Product's image
    var openButton : Button? = null // Button for open the stores space
    var createAccountButton : Button? = null // Button for create an account
    var loginMain : Button? = null // Button for login

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
                putExtra(EXTRA, emptyArray<String>())
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

    // Verifies if already there's a user logged in. If there is, opens it's dashboard.
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
