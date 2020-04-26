package com.example.onpriceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.onpriceapp.model.Store
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        var id = 0

        api.login(name, password).enqueue(object : Callback<Store> {
            override fun onFailure(call: Call<Store>, t: Throwable) {
                Log.e(ERROR, call.toString())
            }

            override fun onResponse(call: Call<Store>, response: Response<Store>) {
                if (response.body() != null)
                {
                    val store = response.body()!!
                    Log.e(ERROR, store.toString())

                    id = store.id
                    Log.e("ID", id.toString())
                }

                if (id != 0) {
                    Toast.makeText(this@StoreLoginActivity, "Usuário Logado com Sucesso!", Toast.LENGTH_SHORT).show()

                    SESSION_LOGIN = true
                    ID_STORE = id

                    startActivity(Intent(this@StoreLoginActivity, StoreProductsActivity::class.java))
                }
                else
                    Toast.makeText(this@StoreLoginActivity, "Nome de usuário ou senha incorretos!", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
