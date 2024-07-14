package com.example.myhealthreminder

import android.app.TimePickerDialog
import android.icu.util.Calendar
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealthreminder.adapters.DaysAdapter
import com.example.myhealthreminder.models.DayModel
import com.example.myhealthreminder.models.ReminderModel
import com.example.myhealthreminder.utils.DataBaseHelper

class CreateActivity : AppCompatActivity() {

    private var TAG: String = "CreateActivity"

    private var dbHelper: DataBaseHelper? = null
    var daysList: ArrayList<DayModel> = ArrayList()

    var daysSelected = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_create)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // enable the back button in the navigation bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = getString(R.string.add_reminder)
//        supportActionBar!!.setHomeAsUpIndicator(R.drawable.back_arrow)

        val cTitleInput: EditText = findViewById(R.id.cTitleInput)
        val cDescriptionInput: EditText = findViewById(R.id.cDescriptionInput)
        val btnRemiderTime: Button = findViewById(R.id.btnRemiderTime)

        // Initialize DBHelper
        dbHelper = DataBaseHelper(this, null, null, 1)

        daysRecyclerView()

        val createBtn: Button = findViewById(R.id.cCreateBtn)

        // Set click listener for the button
        btnRemiderTime.setOnClickListener {
//            Toast.makeText(this, "Pick Time", Toast.LENGTH_SHORT).show()
            val cal = Calendar.getInstance()
            val timeSetListener = TimePickerDialog.OnTimeSetListener { timePicker, hour, minute ->
                cal.set(Calendar.HOUR_OF_DAY, hour)
                cal.set(Calendar.MINUTE, minute)
                btnRemiderTime.text = String.format("%02d:%02d", hour, minute)
            }
            TimePickerDialog(
                this,
                timeSetListener,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true
            ).show()
        }

        createBtn.setOnClickListener {
            // Get data from EditText
            val title = cTitleInput.text.toString()
            val description = cDescriptionInput.text.toString()
            val status = 1
            // arrayList of DayModel Object
            val daysSelectedList: ArrayList<DayModel> = ArrayList()
            // if timepicker is empty and difference from TimeFormat, set it to ""
            if (btnRemiderTime.text.toString()
                    .isEmpty() || btnRemiderTime.text.toString() == "Pick Time"
            ) {
                btnRemiderTime.text = ""
            } else {
                btnRemiderTime.text.toString()
            }

            val timepicker: String = btnRemiderTime.text.toString()
            // remove days from daysSelectedList if
            for (day in daysList) {
                if (day.status) {
//                    Toast.makeText(this, "day: ${day.title}", Toast.LENGTH_SHORT).show()
                    daysSelectedList.add(day)
                }
                // else remove it
                else {
                    daysSelectedList.remove(day)
                }
            }
            daysSelected = ""
            //  daysSelectedList by title and separate by comma
            for (day in daysSelectedList) {
                // add days to daysSelected
                daysSelected = daysSelected.plus("${day.title}, ")
            }
            // remove last comma from daysSelected
            daysSelected = daysSelected.dropLast(2)

            if (title.isEmpty() || description.isEmpty() || daysSelectedList.isEmpty() || timepicker.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                btnRemiderTime.text = "Pick Time"
                return@setOnClickListener
            } else {
                // Create a Reminder object
                val reminder = ReminderModel(
                    title = title,
                    description = description,
                    status = status,
                    reminderDays = daysSelected,
                    reminderTimes = timepicker
                )
                // Insert Reminder to DB
                val responseId: Long = dbHelper!!.insertReminder(reminder)

                if (responseId != -1L) {
                    Toast.makeText(this, "Reminder Created", Toast.LENGTH_SHORT).show()
                    // Pop
                    finish()
                } else {
                    Toast.makeText(this, "Reminder Not Created", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun daysRecyclerView() {
        // 1- RecyclerView
        val daysRecyclerView: RecyclerView = findViewById(R.id.recyclerView_filter)
        daysRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // 2- Data source: List of ReminderModel Object
        var filter1 = DayModel(1, "Mon", true)
        var filter2 = DayModel(2, "Tue", false)
        var filter3 = DayModel(3, "Wed", false)
        var filter4 = DayModel(4, "Thu", false)
        var filter5 = DayModel(5, "Fri", false)
        var filter6 = DayModel(6, "Sat", false)
        var filter7 = DayModel(7, "Sun", false)

        daysList.add(filter1)
        daysList.add(filter2)
        daysList.add(filter3)
        daysList.add(filter4)
        daysList.add(filter5)
        daysList.add(filter6)
        daysList.add(filter7)

        // 3- Adapter
        val adapter = DaysAdapter(daysList)
        daysRecyclerView.adapter = adapter
    }
}