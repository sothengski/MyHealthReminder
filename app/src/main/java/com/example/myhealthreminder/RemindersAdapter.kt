package com.example.myhealthreminder

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RemindersAdapter(val remindersList: ArrayList<ReminderModel>) :
    RecyclerView.Adapter<RemindersAdapter.MyViewHolder>() {

    // ViewHolder: Holds references to the views within
    // each item in the recyclerView
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var reminderImg: ImageView
        var reminderName: TextView
        var reminderDays: TextView
        var reminderTimes: TextView
        var reminderStatus: TextView

        init {
            reminderImg = itemView.findViewById(R.id.item_image)
            reminderName = itemView.findViewById(R.id.item_name)
            reminderDays = itemView.findViewById(R.id.item_days)
            reminderTimes = itemView.findViewById(R.id.item_times)
            reminderStatus = itemView.findViewById(R.id.item_status)

            // Handling the click events on cardViews
            itemView.setOnClickListener() {
                Toast.makeText(
                    itemView.context,
                    "You Clicked: ${remindersList[adapterPosition].reminderName}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        // Create and return a new ViewHolder Instance for each item
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.card_item_layout, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        // Returns the total number of items in the dataset
        return remindersList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        // Bind data to views based on the item at the specified position
        holder.reminderImg.setImageResource(remindersList[position].reminderImg)
        holder.reminderName.setText(remindersList[position].reminderName)
        holder.reminderDays.setText(remindersList[position].reminderDays)
        holder.reminderTimes.setText(remindersList[position].reminderTimes)
        holder.reminderStatus.setText(if (remindersList[position].reminderStatus) "Active" else "Inactive")
        holder.reminderStatus.setTextColor(if (remindersList[position].reminderStatus) Color.BLACK else Color.WHITE)
        holder.reminderStatus.setBackgroundResource(if (remindersList[position].reminderStatus) R.drawable.rounded_corner else R.drawable.rounded_corner_inactive)

    }
}