package com.example.myhealthreminder

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealthreminder.Adapters.FiltersAdapter
import com.example.myhealthreminder.Adapters.RemindersAdapter
import com.example.myhealthreminder.Models.FilterModel
import com.example.myhealthreminder.Models.ReminderModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
        // enable the back button in the navigation bar
        supportActionBar!!.hide()

        reminderRecyclerView()
        filterRecyclerView()

        val fab: FloatingActionButton = findViewById(R.id.fab);
        fab.setOnClickListener() {
            // Navigate to CreateActivity
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
    }

    fun reminderRecyclerView() {
        // 1- RecyclerView
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // 2- Data source: List of ReminderModel Objects
        var remindersList: ArrayList<ReminderModel> = ArrayList()

        var reminder1 = ReminderModel(
            1, "Reminder 1", "Reminder 1 Description", 0, "Dose",
//            (R.drawable.pill_symbol),
            "",
            "2",
            "150mg",
            "Mon | Tue | Wed",
            "9:00 | 12:00 | 18:00",
            "Before Meals",
            300,
            "piano", ""
        )

        var reminder2 = ReminderModel(
            2, "Reminder 2", "Reminder 2 Description", 1, "Drop",
//            (R.drawable.pill_symbol),
            "",            "1",
            "50mg",
            "Mon ",
            "12:00 | 18:00",
            "After Meals",
            300,
            "piano", ""
        )

        var reminder3 = ReminderModel(
            3, "Reminder 3", "Reminder 3 Description", 0, "Tablet",
//            (R.drawable.pill_symbol),
            "",            "3",
            "100mg",
            "Thu | Fri",
            "18:00",
            "After Dinner",
            300,
            "piano", ""
        )

        var reminder4 = ReminderModel(
            4, "Reminder 4", "Reminder 4 Description", 0, "Dose",
//            (R.drawable.pill_symbol),
            "",            "3",
            "100mg",
            "Sat | Sun",
            "6:00 | 12:00 | 18:00",
            "During meals",
            300,
            "piano", ""
        )


        var reminder5 = ReminderModel(
            5, "Reminder 5", "Reminder 5 Description", 1, "Enject",
//            (R.drawable.pill_symbol),
            "",            "5",
            "75mg",
            "Sun",
            "7:00",
            "After Breakfast",
            300,
            "piano",
            ""
        )

        remindersList.add(reminder1)
        remindersList.add(reminder2)
        remindersList.add(reminder3)
        remindersList.add(reminder4)
        remindersList.add(reminder5)

        // 3- Adapter
        val adapter = RemindersAdapter(remindersList)
        recyclerView.adapter = adapter
    }

    fun filterRecyclerView() {
        // 1- RecyclerView
        val filterRecyclerView: RecyclerView = findViewById(R.id.recyclerView_filter)
        filterRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // 2- Data source: List of ReminderModel Objects
        var filtersList: ArrayList<FilterModel> = ArrayList()

        var filter1 = FilterModel(1, "Monday", true)
        var filter2 = FilterModel(2, "Tuesday", false)
        var filter3 = FilterModel(3, "Wednesday", false)
        var filter4 = FilterModel(4, "Thursday", false)
        var filter5 = FilterModel(5, "Friday", false)
        var filter6 = FilterModel(6, "Saturday", false)
        var filter7 = FilterModel(7, "Sunday", false)


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