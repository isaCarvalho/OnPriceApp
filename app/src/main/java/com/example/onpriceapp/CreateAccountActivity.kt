package com.example.onpriceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.onpriceapp.controller.StoreController

class CreateAccountActivity : AppCompatActivity() {

    var createAccountButton : Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        createAccountButton = findViewById(R.id.createButton)
        createAccountButton!!.setOnClickListener {

            val name = findViewById<EditText>(R.id.nameField).text.toString()
            val password = findViewById<EditText>(R.id.passwordField).text.toString()
            val cnpj = findViewById<EditText>(R.id.cnpjField).text.toString()
            val street = findViewById<EditText>(R.id.streetField).text.toString()
            val number = findViewById<EditText>(R.id.numberField).text.toString()
            val bairro = findViewById<EditText>(R.id.bairroField).text.toString()
            val city = findViewById<EditText>(R.id.cityField).text.toString()
            val time = findViewById<EditText>(R.id.timeField).text.toString()

            if (StoreController(this).createAccount(name, password, cnpj, street, number, bairro,
            city, "RJ", time)) {
                Toast.makeText(this, "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, StoreLoginActivity::class.java)
                startActivity(intent)
            }
            else
                Toast.makeText(this, "Não foi possível criar a conta!", Toast.LENGTH_SHORT).show()
        }
    }
}
