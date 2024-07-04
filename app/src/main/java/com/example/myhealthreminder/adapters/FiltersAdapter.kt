package com.example.myhealthreminder.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealthreminder.models.FilterModel
import com.example.myhealthreminder.R

class FiltersAdapter(private val filtersList: ArrayList<FilterModel>) :
    RecyclerView.Adapter<FiltersAdapter.MyViewHolder>() {
    // ViewHolder: Holds references to the views within each item in the recyclerView
    private var listener: OnItemClickListener? = null

    fun setListener(clickListener: OnItemClickListener?) {
        this.listener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.filter_card_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return filtersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Bind data to views based on the item at the specified position
        val filterData: FilterModel = filtersList[position]
        holder.bindItems(filterData)

        // Set click listener for the card view
        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.itemView.context,
                "You Clicked: ${filterData.filterName}",
                Toast.LENGTH_SHORT
            ).show()
            // Handle the click event here
            // For example, you can update the filter status and notify the adapter
            filterData.filterStatus = !filterData.filterStatus
            notifyDataSetChanged()

//            if (listener != null) {
//                listener!!.onItemClick(filterData, position)
//            }
        }
    }

    class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(filterData: FilterModel) {
            val filterCard = itemView.findViewById<CardView>(R.id.filter_card)
            val filterName = itemView.findViewById<TextView>(R.id.txtv_day)
//            filterCard.text = filterData.filterName
            filterName.text = filterData.filterName

            filterCard.setCardBackgroundColor(if (filterData.filterStatus) Color.DKGRAY else Color.WHITE)
            filterName.text = filterData.filterName
            filterName.setTextColor(if (filterData.filterStatus) Color.WHITE else Color.BLACK)
        }
    }

    // Create an interface to handle click events
    interface OnItemClickListener {
        fun onItemClick(filterData: FilterModel?, position: Int)
    }
}