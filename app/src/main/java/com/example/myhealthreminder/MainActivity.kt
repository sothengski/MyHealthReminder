package com.example.myhealthreminder

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // 1- RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 2- Data source: List of ReminderModel Objects
        var remindersList: ArrayList<ReminderModel> = ArrayList()

        var reminder1 = ReminderModel(R.drawable.pill_symbol, "Reminder 1", "Mon", "9:00", true)
        var reminder2 =
            ReminderModel(R.drawable.pill_symbol, "Reminder 2", "Mon | Tue", "9:00", true)
        var reminder3 = ReminderModel(
            R.drawable.pill_symbol,
            "Reminder 3",
            "Mon | Sat",
            "12:00 | 6:00",
            false
        )
        var reminder4 = ReminderModel(
            R.drawable.pill_symbol,
            "Reminder 4",
            "Fri | Sun",
            "9:00 | 11:00 | 1:00",
            false
        )
        var reminder5 = ReminderModel(
            R.drawable.pill_symbol,
            "Reminder 5",
            "Wed | Thu",
            "6:00 | 12:00 | 18:00 | 0:00",
            false
        )

        remindersList.add(reminder1)
        remindersList.add(reminder2)
        remindersList.add(reminder3)
        remindersList.add(reminder4)
        remindersList.add(reminder5)

        // 3- Adapter
        val adapter = RemindersAdapter(remindersList)
        recyclerView.adapter = adapter

        filterRecyclerView()
    }

    fun filterRecyclerView() {
        // 1- RecyclerView
        val filterRecyclerView: RecyclerView = findViewById(R.id.recyclerView_filter)
        filterRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // 2- Data source: List of ReminderModel Objects
        var filtersList: ArrayList<FilterModel> = ArrayList()

        var filter1 = FilterModel(1, "Monday",true)
        var filter2 = FilterModel(2, "Tuesday",false)
        var filter3 = FilterModel(3, "Wednesday",false)
        var filter4 = FilterModel(4, "Thursday",false)
        var filter5 = FilterModel(5, "Friday",false)
        var filter6 = FilterModel(6, "Saturday",false)
        var filter7 = FilterModel(7, "Sunday",false)


        filtersList.add(filter1)
        filtersList.add(filter2)
        filtersList.add(filter3)
        filtersList.add(filter4)
        filtersList.add(filter5)
        filtersList.add(filter6)
        filtersList.add(filter7)


        // 3- Adapter
        val adapter = FiltersAdapter(filtersList)
        filterRecyclerView.adapter = adapter
    }
}