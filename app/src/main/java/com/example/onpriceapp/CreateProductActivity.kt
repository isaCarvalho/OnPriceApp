package com.example.onpriceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.example.onpriceapp.controller.ProductController

class CreateProductActivity : AppCompatActivity() {

    var id : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_product)

        id = intent.getIntExtra(EXTRA, -1)
    }

    private fun insert(store_id: Int)
    {
        val name = findViewById<EditText>(R.id.productNameField).text.toString()
        val qt = findViewById<EditText>(R.id.qtField).text.toString().toInt()
        val price = findViewById<EditText>(R.id.priceField).text.toString()
        val unity = findViewById<EditText>(R.id.unityField).text.toString()

        if (validate(name) && validate(qt.toString()) && validate(price) && validate(unity)) {

            if (ProductController(this).insert(name, "Limpeza", price, qt, unity, store_id))
                Toast.makeText(this, "Produto criado com sucesso!", Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(this, "Não foi possível criar o produto!", Toast.LENGTH_SHORT).show()
        }
        else
            Toast.makeText(this, "Todos os campos são obrigatórios!", Toast.LENGTH_SHORT).show()
    }

    private fun validate(field : String) : Boolean
    {
        return !field.isEmpty();
    }
}
