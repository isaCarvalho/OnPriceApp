package com.example.onpriceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import androidx.cardview.widget.CardView

class MainActivity : AppCompatActivity() {

    var cardStore : ImageView? = null
    var createAccountButton : Button? = null
    var loginMain : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        cardStore = findViewById(R.id.cardStoreImage)

        cardStore!!.setOnClickListener {
            val intent = Intent(this, StoreLoginActivity::class.java)
            startActivity(intent)
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
            val intent = Intent(this, StoreLoginActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }
}
