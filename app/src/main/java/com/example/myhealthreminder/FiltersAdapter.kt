package com.example.myhealthreminder

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.color.MaterialColors

class FiltersAdapter(val filtersList: ArrayList<FilterModel>) :
    RecyclerView.Adapter<FiltersAdapter.MyViewHolder>() {
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val filterCard: CardView
        val filterName: TextView

        init {
            filterCard = itemView.findViewById(R.id.filter_card)
            filterName = itemView.findViewById(R.id.txtv_day)
        }
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
        holder.filterCard.setCardBackgroundColor(if (filtersList[position].filterStatus) Color.DKGRAY else Color.WHITE)
        holder.filterName.setText(filtersList[position].filterName)
        holder.filterName.setTextColor(if (filtersList[position].filterStatus) Color.WHITE else Color.BLACK)
    }

}