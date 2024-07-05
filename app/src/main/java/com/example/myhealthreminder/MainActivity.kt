package com.example.myhealthreminder

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealthreminder.adapters.DaysAdapter
import com.example.myhealthreminder.adapters.RemindersAdapter
import com.example.myhealthreminder.models.DayModel
import com.example.myhealthreminder.models.ReminderModel
import com.example.myhealthreminder.utils.DataBaseHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private var TAG: String = "MainActivity"

    private var remindersList: ArrayList<ReminderModel> = ArrayList()
    private var daysList: ArrayList<DayModel> = ArrayList()
    private var myAdapter: RemindersAdapter? = null
    private var recyclerView: RecyclerView? = null

    //    private var layoutManager: RecyclerView.LayoutManager? = null
    private var dbHelper: DataBaseHelper? = null


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

        // Initialize DBHelper
        dbHelper = DataBaseHelper(this, null, null, 1)

        // Get all reminders from DB
        getAllReminder()

        // Filter RecyclerView
        filterRecyclerView()
//        reminderRecyclerView()

        // Floating Action Button
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            // Navigate to CreateActivity
            val intent = Intent(this, CreateActivity::class.java)
            startActivity(intent)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onResume() {
        super.onResume()
        if (myAdapter != null) {
            getAllReminder()
            myAdapter!!.notifyDataSetChanged()
        }
    }

    private fun getAllReminder() {
        recyclerView = findViewById(R.id.recyclerView)

        // Get all reminders from DB
        remindersList = dbHelper!!.getAllReminders() as ArrayList<ReminderModel>

        myAdapter = RemindersAdapter(remindersList) // Initialize Adapter

        recyclerView!!.setHasFixedSize(true) // Use fixed size
        recyclerView!!.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView!!.adapter = myAdapter // Set Adapter
    }

//    fun reminderRecyclerView() {
//        // 1- RecyclerView
//        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)
//        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
//
//        // 2- Data source: List of ReminderModel Objects
//        val remindersList: ArrayList<ReminderModel> = ArrayList()
//
//        val reminder1 = ReminderModel(
//            1, "Reminder 1", "Reminder 1 Description", 0, "Dose",
////            (R.drawable.pill_symbol),
//            "",
//            "2",
//            "150mg",
//            "Mon | Tue | Wed",
//            "9:00 | 12:00 | 18:00",
//            "Before Meals",
//            300,
//            "piano", ""
//        )
//
//        val reminder2 = ReminderModel(
//            2, "Reminder 2", "Reminder 2 Description", 1, "Drop",
////            (R.drawable.pill_symbol),
//            "", "1",
//            "50mg",
//            "Mon ",
//            "12:00 | 18:00",
//            "After Meals",
//            300,
//            "piano", ""
//        )
//
//        val reminder3 = ReminderModel(
//            3, "Reminder 3", "Reminder 3 Description", 0, "Tablet",
////            (R.drawable.pill_symbol),
//            "", "3",
//            "100mg",
//            "Thu | Fri",
//            "18:00",
//            "After Dinner",
//            300,
//            "piano", ""
//        )
//
//        val reminder4 = ReminderModel(
//            4, "Reminder 4", "Reminder 4 Description", 0, "Dose",
////            (R.drawable.pill_symbol),
//            "", "3",
//            "100mg",
//            "Sat | Sun",
//            "6:00 | 12:00 | 18:00",
//            "During meals",
//            300,
//            "piano", ""
//        )
//
//
//        val reminder5 = ReminderModel(
//            5, "Reminder 5", "Reminder 5 Description", 1, "Inject",
////            (R.drawable.pill_symbol),
//            "", "5",
//            "75mg",
//            "Sun",
//            "7:00",
//            "After Breakfast",
//            300,
//            "piano",
//            ""
//        )
//
//        remindersList.add(reminder1)
//        remindersList.add(reminder2)
//        remindersList.add(reminder3)
//        remindersList.add(reminder4)
//        remindersList.add(reminder5)
//
//        // 3- Adapter
//        val adapter = RemindersAdapter(remindersList)
//        recyclerView.adapter = adapter
//    }

    private fun filterRecyclerView() {
        // 1- RecyclerView
        val filterRecyclerView: RecyclerView = findViewById(R.id.recyclerView_filter)
        filterRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // 2- Data source: List of ReminderModel Objects
        val filtersList: ArrayList<DayModel> = ArrayList()

        val filter1 = DayModel(1, "Monday", true)
        val filter2 = DayModel(2, "Tuesday", false)
        val filter3 = DayModel(3, "Wednesday", false)
        val filter4 = DayModel(4, "Thursday", false)
        val filter5 = DayModel(5, "Friday", false)
        val filter6 = DayModel(6, "Saturday", false)
        val filter7 = DayModel(7, "Sunday", false)


        filtersList.add(filter1)
        filtersList.add(filter2)
        filtersList.add(filter3)
        filtersList.add(filter4)
        filtersList.add(filter5)
        filtersList.add(filter6)
        filtersList.add(filter7)

        // 3- Adapter
        val adapter = DaysAdapter(filtersList)
        filterRecyclerView.adapter = adapter
    }
}