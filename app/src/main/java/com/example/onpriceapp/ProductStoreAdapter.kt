package com.example.onpriceapp

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.onpriceapp.api.APIController
import com.example.onpriceapp.model.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.await

class ProductStoreAdapter(private var myDataset : ArrayList<Product>, private val store_id: Int) :
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

        holder.imageDelete.setOnClickListener{v ->
            val product = myDataset[position]

            GlobalScope.launch(Dispatchers.IO) {
                api.deleteProduct(myDataset[position].id)
            }

            val products = ArrayList<Product>()

            GlobalScope.launch(Dispatchers.IO) {
                val response = api.listProducts(ID_STORE).await()

                for (product in response)
                    products.add(product)
            }

            myDataset = products
            notifyDataSetChanged()
        }

        holder.imageEdit.setOnClickListener{v ->
            val product = myDataset[position]
            val array = arrayOf<String>(product.id.toString(), product.name, product.unit, product.qt.toString(), product.price, product.stamp, store_id.toString())

            val intent = Intent(v.context, CreateProductActivity::class.java).apply {
                putExtra(EXTRA, array);
            }

            startActivity(v.context, intent, null)
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