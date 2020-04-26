package com.example.onpriceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.onpriceapp.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateProductActivity : AppCompatActivity() {

    private var storeId : Int = 0
    private var productId : Int = 0
    private var saveButton : Button? = null
    private var array : Array<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_product)

        storeId = ID_STORE
        array = intent.getStringArrayExtra(EXTRA)

        if (!array.isNullOrEmpty())
        {
            productId = array!![0].toInt()

            findViewById<EditText>(R.id.productNameField).setText(array!![1])
            findViewById<EditText>(R.id.unityField).setText(array!![2])
            findViewById<EditText>(R.id.qtField).setText(array!![3])
            findViewById<EditText>(R.id.priceField).setText(array!![4])
            findViewById<EditText>(R.id.productStampField).setText(array!![5])

            storeId = array!![6].toInt()
        }

        saveButton = findViewById(R.id.saveButton)
        saveButton!!.setOnClickListener {
            if (array.isNullOrEmpty())
                putData(storeId)
            else
                putData(storeId, true)
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
                val product = Product(DEFAULT_INT_VALUE, name, category, price, stamp, qt, unity, store_id)

                api.insertProduct(product).enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(this@CreateProductActivity, "Não foi possível criar o produto!", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        Toast.makeText(this@CreateProductActivity, "Produto criado com sucesso!", Toast.LENGTH_SHORT).show()

                        finish()
                    }
                })
            }
            else
            {
                val product = Product(productId, name, category, price, stamp, qt, unity, store_id)

                api.updateProduct(productId, product).enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(this@CreateProductActivity, "Não foi possível editar o produto!", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        Toast.makeText(this@CreateProductActivity, "Produto editado com sucesso!", Toast.LENGTH_SHORT).show()

                        finish()
                    }
                })
            }
        }
        else
            Toast.makeText(this, "Todos os campos são obrigatórios!", Toast.LENGTH_SHORT).show()
    }

    private fun validate(field : String) : Boolean = field.isNotEmpty()
}
