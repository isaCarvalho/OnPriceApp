package com.example.onpriceapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.onpriceapp.R
import com.example.onpriceapp.model.Product

class ListProductsAdapter(private var myDataset : ArrayList<Product>)
    : RecyclerView.Adapter<ListProductsAdapter.MyViewHolder>(), Filterable
{
    private var listCopy : ArrayList<Product> = ArrayList(myDataset.toMutableList())

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

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filterList = ArrayList<Product>()

                if (constraint.isNullOrEmpty())
                    filterList.addAll(listCopy)
                else {
                    val filterPattern = constraint.toString().toLowerCase().trim()

                    listCopy.forEach { item ->
                        if (item.name.toLowerCase().contains(filterPattern))
                            filterList.add(item)
                    }
                }

                val results = FilterResults()
                results.values = filterList

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