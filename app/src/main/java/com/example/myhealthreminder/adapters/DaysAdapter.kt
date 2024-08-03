package com.example.myhealthreminder.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealthreminder.models.DayModel
import com.example.myhealthreminder.R

class DaysAdapter(
    private val daysList: ArrayList<DayModel>,
    private val isClickable: Boolean = true
) :
    RecyclerView.Adapter<DaysAdapter.MyViewHolder>() {
    // ViewHolder: Holds references to the views within each item in the recyclerView
    private var listener: OnItemClickListener? = null

    fun setListener(clickListener: OnItemClickListener?) {
        this.listener = clickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.day_card_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return daysList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Bind data to views based on the item at the specified position
        val filterData: DayModel = daysList[position]
        holder.bindItems(filterData)

        if (!isClickable) {
            holder.itemView.setOnClickListener(null)
        } else {
            // Set click listener for the card view
            holder.itemView.setOnClickListener {
//            Toast.makeText(
//                holder.itemView.context,
//                "You Clicked: ${filterData.title}",
//                Toast.LENGTH_SHORT
//            ).show()
                // Handle the click event here
                filterData.status = !filterData.status
                notifyDataSetChanged()

//            if (listener != null) {
//                listener!!.onItemClick(filterData, position)
//            }
            }
        }


    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(filterData: DayModel) {
            val filterCard = itemView.findViewById<CardView>(R.id.filter_card)
            val filterName = itemView.findViewById<TextView>(R.id.txtv_day)
//            filterCard.text = filterData.filterName
            filterName.text = filterData.title

            filterCard.setCardBackgroundColor(if (filterData.status) Color.DKGRAY else Color.WHITE)
            filterName.text = filterData.title
            filterName.setTextColor(if (filterData.status) Color.WHITE else Color.BLACK)
        }
    }

    // Create an interface to handle click events
    interface OnItemClickListener {
        fun onItemClick(filterData: DayModel?, position: Int)
    }
}