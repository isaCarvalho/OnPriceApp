package com.example.onpriceapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.onpriceapp.controller.ProductController

class CreateProductActivity : AppCompatActivity() {

    var id : Int = 0
    var product_id : Int = 0
    var saveButton : Button? = null
    var array : Array<String>? = null

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
            product_id = array!![0].toInt()
        }

        val storeId = findViewById<TextView>(R.id.storeName)
        storeId.text = id.toString()

        saveButton = findViewById(R.id.saveButton)
        saveButton!!.setOnClickListener {
            if (array.isNullOrEmpty())
                putData(id)
            else
                putData(id, true, product_id)
        }
    }

    private fun putData(store_id: Int, update : Boolean = false, product_id : Int = -1)
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
                if (ProductController(this).insert(name, category, price, stamp, qt, unity, store_id))
                    Toast.makeText(this, "Produto criado com sucesso!", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "Não foi possível criar o produto!", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, StoreProductsActivity::class.java))
            }
            else
            {
                if (ProductController(this).update(product_id, name, category, price, stamp, qt, unity))
                    Toast.makeText(this, "Produto editado com sucesso!", Toast.LENGTH_SHORT).show()
                else
                    Toast.makeText(this, "Não foi possível editar o produto!", Toast.LENGTH_SHORT).show()

                startActivity(Intent(this, StoreProductsActivity::class.java))
            }

        }
        else
            Toast.makeText(this, "Todos os campos são obrigatórios!", Toast.LENGTH_SHORT).show()
    }

    private fun validate(field : String) : Boolean
    {
        return !field.isEmpty();
    }
}
