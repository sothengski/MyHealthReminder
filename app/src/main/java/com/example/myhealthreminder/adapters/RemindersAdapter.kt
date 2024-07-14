package com.example.myhealthreminder.adapters

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealthreminder.DetailActivity
import com.example.myhealthreminder.models.ReminderModel
import com.example.myhealthreminder.R

class RemindersAdapter(val remindersList: ArrayList<ReminderModel>) :
    RecyclerView.Adapter<RemindersAdapter.MyViewHolder>() {

    // ViewHolder: Holds references to the views within
    // each item in the recyclerView
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(reminder: ReminderModel) {
            val reminderImg = itemView.findViewById<ImageView>(R.id.item_image)
            val reminderName = itemView.findViewById<TextView>(R.id.item_name)
            val reminderDays = itemView.findViewById<TextView>(R.id.item_days)
            val reminderTimes = itemView.findViewById<TextView>(R.id.item_times)
            val reminderStatus = itemView.findViewById<TextView>(R.id.item_status)

            reminderImg.setImageResource(R.drawable.pill_symbol)
            reminderName.text = reminder.title
            reminderDays.text = reminder.reminderDays
            reminderTimes.text = reminder.reminderTimes
            reminderStatus.text = if (reminder.status == 1) "Active" else "Inactive"
            reminderStatus.setTextColor(if (reminder.status == 1) Color.BLACK else Color.WHITE)
            reminderStatus.setBackgroundResource(if (reminder.status == 1) R.drawable.rounded_corner else R.drawable.rounded_corner_inactive)
            // Set click listener for the card view
            itemView.setOnClickListener {
//                Toast.makeText(
//                    itemView.context,
//                    "You Clicked: ${reminder.title}",
//                    Toast.LENGTH_SHORT
//                ).show()

                val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                    putExtra("image", R.drawable.pill_symbol)
                    putExtra("reminderData", reminder)
                }
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
        holder.bind(remindersList[position])
    }


    private var onItemClickListener: ((String) -> Unit)? = null
    fun setOnItemClickListener(listener: (String) -> Unit) {
        onItemClickListener = listener
//        Log.d("TAG", "clickListener: ")
    }
}