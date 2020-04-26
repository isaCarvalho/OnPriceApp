package com.example.onpriceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class CreateProductActivity : AppCompatActivity() {

    private var id : Int = 0
    private var productId : Int = 0
    private var saveButton : Button? = null
    private var array : Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_product)

        id = intent.getIntExtra(EXTRA, -1)
        array = intent.getStringArrayExtra(EXTRA)

        if (!array.isNullOrEmpty())
        {
            findViewById<EditText>(R.id.productNameField).setText(array!![1])
            findViewById<EditText>(R.id.unityField).setText(array!![2])
            findViewById<EditText>(R.id.qtField).setText(array!![3])
            findViewById<EditText>(R.id.priceField).setText(array!![4])
            findViewById<EditText>(R.id.productStampField).setText(array!![5])

            id = array!![6].toInt()
            productId = array!![0].toInt()
        }

        saveButton = findViewById(R.id.saveButton)
        saveButton!!.setOnClickListener {
            if (array.isNullOrEmpty())
                putData(id)
            else
                putData(id, true)
        }
    }

    private fun putData(store_id: Int, update : Boolean = false)
    {
        val name = findViewById<EditText>(R.id.productNameField).text.toString()
        val qt = findViewById<EditText>(R.id.qtField).text.toString().toInt()
        val price = findViewById<EditText>(R.id.priceField).text.toString()
        val unity = findViewById<EditText>(R.id.unityField).text.toString()
        val stamp = findViewById<EditText>(R.id.productStampField).text.toString()
        val category = findViewById<Spinner>(R.id.categorySpinner).selectedItem.toString()

        if (validate(name) && validate(qt.toString()) && validate(price) && validate(unity) && validate(stamp)) {

            if (!update)
            {
                try {
                    GlobalScope.launch(Dispatchers.IO) {
                        api.insertProduct(name, category, price, stamp, qt, unity, store_id)
                    }

                    Toast.makeText(this, "Produto criado com sucesso!", Toast.LENGTH_SHORT).show()
                }
                catch (e: Exception) {
                    Toast.makeText(this, "Não foi possível criar o produto!", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            else
            {
                try {
                    GlobalScope.launch(Dispatchers.IO) {
                        api.updateProduct(id, name, category, price, stamp, qt, unity)
                    }

                    Toast.makeText(this, "Produto editado com sucesso!", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, "Não foi possível editar o produto!", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            finish()
        }
        else
            Toast.makeText(this, "Todos os campos são obrigatórios!", Toast.LENGTH_SHORT).show()
    }

    private fun validate(field : String) : Boolean
    {
        return field.isNotEmpty();
    }
}
