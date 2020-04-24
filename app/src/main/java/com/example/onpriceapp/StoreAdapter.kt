package com.example.onpriceapp

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.onpriceapp.model.Store

class StoreAdapter(private var myDataset : ArrayList<Store>) :
    RecyclerView.Adapter<StoreAdapter.MyViewHolder>(), Filterable
{
    private val listCopy = ArrayList<Store>(myDataset.toMutableList())

    class MyViewHolder (v: View) : RecyclerView.ViewHolder(v)
    {
        val name : TextView = v.findViewById(R.id.storeName)
        val address : TextView = v.findViewById(R.id.endereco)
        val time : TextView = v.findViewById(R.id.horarioFunc)
        val buttonOpen : Button = v.findViewById(R.id.openStore)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_store, parent, false)

        return MyViewHolder(v)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.name.text = myDataset[position].name
        holder.address.text = "Rua ${myDataset[position].street}, Nº ${myDataset[position].number}" +
                ", Bairro ${myDataset[position].bairro} - ${myDataset[position].city}"
        holder.time.text = "Aberto até: ${myDataset[position].time}"

        holder.buttonOpen.setOnClickListener {v ->
            val intent = Intent(v.context, ProductsActivity::class.java).apply {
                putExtra(EXTRA, myDataset[position].id)
            }

            startActivity(v.context, intent, null)
        }
    }

    override fun getItemCount(): Int = myDataset.size

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {

                val filterList = ArrayList<Store>()

                if (constraint.isNullOrEmpty())
                    filterList.addAll(listCopy)
                else
                {
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
                myDataset = results!!.values as ArrayList<Store>

                notifyDataSetChanged()
            }
        }
    }
}