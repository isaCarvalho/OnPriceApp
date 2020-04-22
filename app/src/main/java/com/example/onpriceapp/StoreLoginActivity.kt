package com.example.onpriceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.onpriceapp.controller.StoreController

class StoreLoginActivity : AppCompatActivity() {

    var loginButton : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_login)

        loginButton = findViewById(R.id.loginButton)
        loginButton!!.setOnClickListener {
            this.login()
        }
    }

    override fun onResume() {
        super.onResume()
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

            else -> false
        }
    }

    private fun login()
    {
        val name = findViewById<EditText>(R.id.userNameEditTxt).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()

        val id = StoreController(this).login(name, password)
        if (id != -1) {
            Toast.makeText(this, "Usuário Logado com Sucesso!", Toast.LENGTH_SHORT).show()

            SESSION_LOGIN = true
            ID_STORE = id

            val intent = Intent(this, StoreProductsActivity::class.java).apply {
                putExtra(EXTRA, id)
            }
            startActivity(intent)
        }
        else
            Toast.makeText(this, "Nome de usuário ou senha incorretos!", Toast.LENGTH_SHORT).show()
    }
}
