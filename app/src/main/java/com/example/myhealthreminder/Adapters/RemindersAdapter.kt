package com.example.myhealthreminder.Adapters

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealthreminder.DetailActivity
import com.example.myhealthreminder.Models.ReminderModel
import com.example.myhealthreminder.R

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
                    "You Clicked: ${remindersList[adapterPosition].title}",
                    Toast.LENGTH_SHORT
                ).show()

                // Navigate to DetailActivity
                val intent = Intent(itemView.context, DetailActivity::class.java)

                // Pass data to DetailActivity
//                intent.putExtra("image", remindersList[adapterPosition].img)
                intent.putExtra("image", R.drawable.pill_symbol)
                intent.putExtra("title", remindersList[adapterPosition].title)
                intent.putExtra("description", remindersList[adapterPosition].reminderDays)
                itemView.context.startActivity(intent)
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
//        holder.reminderImg.setImageResource(remindersList[position].img)
        holder.reminderImg.setImageResource(R.drawable.pill_symbol)
        holder.reminderName.setText(remindersList[position].title)
        holder.reminderDays.setText(remindersList[position].reminderDays)
        holder.reminderTimes.setText(remindersList[position].reminderTimes)
        holder.reminderStatus.setText(if (remindersList[position].status == 1) "Active" else "Inactive")
        holder.reminderStatus.setTextColor(if (remindersList[position].status == 1) Color.BLACK else Color.WHITE)
        holder.reminderStatus.setBackgroundResource(if (remindersList[position].status == 1) R.drawable.rounded_corner else R.drawable.rounded_corner_inactive)
    }
}