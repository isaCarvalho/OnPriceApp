package com.example.onpriceapp.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.onpriceapp.*
import com.example.onpriceapp.model.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductStoreAdapter(private var myDataset : ArrayList<Product>, private val id_store: Int) :
    RecyclerView.Adapter<ProductStoreAdapter.MyViewHolder>(), Filterable
{
    private val listCopy = ArrayList<Product>(myDataset.toMutableList())

    class MyViewHolder(v : View) : RecyclerView.ViewHolder(v)
    {
        val nameTxt : TextView = v.findViewById(R.id.productStoreName)
        val stampTxt : TextView = v.findViewById(R.id.productStoreStamp)
        val priceTxt : TextView = v.findViewById(R.id.productStorePrice)
        val qtUnityTxt : TextView = v.findViewById(R.id.productStoreUnityQt)
        val imageDelete : ImageView = v.findViewById(R.id.trashBlackIcon)
        val imageEdit : ImageView = v.findViewById(R.id.editBlackIcon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder
    {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_product_store, parent, false)

        return MyViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameTxt.text = myDataset[position].name
        holder.stampTxt.text = myDataset[position].stamp
        holder.priceTxt.text = myDataset[position].price
        holder.qtUnityTxt.text = "${myDataset[position].qt} ${myDataset[position].unit}"

        holder.imageEdit.setOnClickListener {v ->
            val array = arrayOf(
                myDataset[position].id.toString(),
                myDataset[position].name,
                myDataset[position].stamp,
                myDataset[position].price,
                myDataset[position].qt.toString(),
                myDataset[position].unit,
                myDataset[position].id_store.toString()
            )

            val intent = Intent(v.context, CreateProductActivity::class.java).apply {
                putExtra(EXTRA, myDataset[position].id_store)
                putExtra(EXTRA, array)
            }

            startActivity(v.context, intent, null)
        }

        holder.imageDelete.setOnClickListener {v ->
            api.deleteProduct(myDataset[position].id).enqueue(object : Callback<String> {
                override fun onFailure(call: Call<String>, t: Throwable) {
                    Log.e(ERROR, call.toString())
                    Toast.makeText(v.context, "Não foi possível deletar o produto!", Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(call: Call<String>, response: Response<String>) {
                    Log.d("DELETE", call.toString())
                    Toast.makeText(v.context, "Produto deletado!", Toast.LENGTH_SHORT)
                        .show()

                    myDataset.removeAt(position)
                    notifyDataSetChanged()
                }
            })
        }
    }

    override fun getItemCount(): Int = myDataset.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val listFilter = ArrayList<Product>()

                if (constraint.isNullOrEmpty())
                    listFilter.addAll(listCopy)
                else
                {
                    val filterPattern = constraint.toString().toLowerCase().trim()

                    listCopy.forEach { item ->
                        if (item.name.toLowerCase().contains(filterPattern))
                            listFilter.add(item)
                    }
                }

                val results = FilterResults()
                results.values = listFilter

                return results
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                myDataset.clear()
                myDataset = results!!.values as ArrayList<Product>

                notifyDataSetChanged()
            }
        }
    }
}