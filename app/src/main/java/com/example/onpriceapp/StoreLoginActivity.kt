package com.example.onpriceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.onpriceapp.controller.StoreController

class StoreLoginActivity : AppCompatActivity() {

    var createAccountButton : Button? = null
    var loginButton : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_store_login)

        createAccountButton = findViewById(R.id.createAccountButton)
        createAccountButton!!.setOnClickListener {
            val intent = Intent(this, CreateAccountActivity::class.java).apply {
                putExtra(EXTRA, arrayOf<String>())
            }
            startActivity(intent)
        }

        loginButton = findViewById(R.id.loginButton)
        loginButton!!.setOnClickListener {
            this.login()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun login()
    {
        val name = findViewById<EditText>(R.id.userNameEditTxt).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()

        val id = StoreController(this).login(name, password)
        if (id != -1) {
            Toast.makeText(this, "Usuário Logado com Sucesso!", Toast.LENGTH_SHORT).show()

            val intent = Intent(this, StoreProductsActivity::class.java).apply {
                putExtra(EXTRA, id)
            }
            startActivity(intent)
        }
        else
            Toast.makeText(this, "Nome de usuário ou senha incorretos!", Toast.LENGTH_SHORT).show()
    }
}