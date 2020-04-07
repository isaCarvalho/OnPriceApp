package com.example.onpriceapp

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.onpriceapp.controller.ProductController
import com.example.onpriceapp.model.Product

class ProductStoreAdapter(private var myDataset : Array<Product>, private val store_id: Int) :
    RecyclerView.Adapter<ProductStoreAdapter.MyViewHolder>()
{
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

            ProductController(v.context).delete(product.id)

            myDataset = ProductController(v.context).list(store_id)
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
}