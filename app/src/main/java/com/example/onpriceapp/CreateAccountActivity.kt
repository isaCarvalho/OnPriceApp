package com.example.onpriceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.onpriceapp.model.Store
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateAccountActivity : AppCompatActivity() {

    var createAccountButton : Button? = null
    var array : Array<String>? = arrayOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_account)

        array = intent.getStringArrayExtra(EXTRA)

        if (!array.isNullOrEmpty())
        {
            findViewById<EditText>(R.id.nameField).setText(array!![1])
            findViewById<EditText>(R.id.passwordField).setText(array!![2])
            findViewById<EditText>(R.id.cnpjField).setText(array!![3])
            findViewById<EditText>(R.id.streetField).setText(array!![4])
            findViewById<EditText>(R.id.numberField).setText(array!![5])
            findViewById<EditText>(R.id.bairroField).setText(array!![6])
            findViewById<EditText>(R.id.cityField).setText(array!![7])
            findViewById<EditText>(R.id.timeField).setText(array!![8])
        }

        createAccountButton = findViewById(R.id.createButton)
        createAccountButton!!.setOnClickListener {
            if (array == null || array!!.isEmpty())
                this.putData(false)
            else
            {
                this.putData(true, array!![0].toInt())
            }
        }
    }

    private fun putData(update : Boolean, id : Int = -1)
    {
        val name = findViewById<EditText>(R.id.nameField).text.toString()
        val password = findViewById<EditText>(R.id.passwordField).text.toString()
        val cnpj = findViewById<EditText>(R.id.cnpjField).text.toString()
        val street = findViewById<EditText>(R.id.streetField).text.toString()
        val number = findViewById<EditText>(R.id.numberField).text.toString()
        val bairro = findViewById<EditText>(R.id.bairroField).text.toString()
        val city = findViewById<EditText>(R.id.cityField).text.toString()
        val time = findViewById<EditText>(R.id.timeField).text.toString()
        val uf = findViewById<Spinner>(R.id.spinner).selectedItem.toString()

        if (validate(name) && validate(password) && validate(cnpj) && validate(street) && validate(number)
            && validate(bairro) && validate(city) && validate(time)) {

            if (!update)
            {
                val store = Store(-1, name, password, cnpj, street, number, bairro, city, uf, time)

                api.insertStore(store).enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(this@CreateAccountActivity,
                            "Conta criada com sucesso!", Toast.LENGTH_SHORT).show()

                        finish()
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        Toast.makeText(this@CreateAccountActivity,
                            "Não foi possível criar a conta!", Toast.LENGTH_SHORT)
                            .show()
                    }
                })
            }
            else
            {
                val store = Store(id, name, password, cnpj, street, number, bairro, city, uf, time)

                api.updateStore(id, store).enqueue(object : Callback<String> {
                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(this@CreateAccountActivity,
                            "Não foi possível atualizar os dados!", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        Toast.makeText(this@CreateAccountActivity,
                            "Dados atualizados com sucesso!", Toast.LENGTH_SHORT).show()

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
