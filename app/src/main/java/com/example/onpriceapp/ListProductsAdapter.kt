package com.example.onpriceapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.onpriceapp.model.Product

class ListProductsAdapter(private var myDataset : Array<Product>)
    : RecyclerView.Adapter<ListProductsAdapter.MyViewHolder>()
{
    class MyViewHolder(v: View) : RecyclerView.ViewHolder(v)
    {
        val name : TextView = v.findViewById(R.id.productName)
        val stamp : TextView = v.findViewById(R.id.productStamp)
        val price : TextView = v.findViewById(R.id.productPrice)
        val qtUnity : TextView = v.findViewById(R.id.productUnityQt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_product, parent, false)

        return MyViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = myDataset[position].name
        holder.price.text = myDataset[position].price
        holder.stamp.text = myDataset[position].stamp
        holder.qtUnity.text = "${myDataset[position].qt} ${myDataset[position].unit}"

    }

    override fun getItemCount(): Int = myDataset.size
}