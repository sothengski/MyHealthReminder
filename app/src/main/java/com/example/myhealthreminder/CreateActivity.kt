package com.example.myhealthreminder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealthreminder.Adapters.FiltersAdapter
import com.example.myhealthreminder.Models.FilterModel
import com.example.myhealthreminder.Models.ReminderModel
import com.example.myhealthreminder.Utils.DataBaseHelper

class CreateActivity : AppCompatActivity() {

    private var TAG: String = "CreateActivity"

//    override fun onSupportNavigateUp(): Boolean {
//        onBackPressed()
//        return true
//    }
//    private var filtersList: ArrayList<FilterModel> = ArrayList()

//    private var myAdapter: FiltersAdapter? = null

//    private var recyclerView: RecyclerView? = null

//    private var layoutManager: RecyclerView.LayoutManager? = null

    private var dbHelper: DataBaseHelper? = null

//    private var reminder = ArrayList<ReminderModel>()

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
//        val cTypeInput: EditText = findViewById(R.id.cTypeInput)
//        val cAmountInput: EditText = findViewById(R.id.cAmountInput)
//        val cCapSizeInput: EditText = findViewById(R.id.cCapSizeInput)
//        val cDaysInput: EditText = findViewById(R.id.cDaysInput)
//        val cTimesInput: EditText = findViewById(R.id.cTimesInput)
//        val cMealInput: EditText = findViewById(R.id.cMealInput)
//        val cSnoozeInput: EditText = findViewById(R.id.cSnoozeInput)
//        val cSoundInput: EditText = findViewById(R.id.cSoundInput)


        // Initialize DBHelper
        dbHelper = DataBaseHelper(this, null, null, 1)


        filterRecyclerView()

        val createBtn: Button = findViewById(R.id.cCreateBtn);
        createBtn.setOnClickListener() {
            // Get data from EditText
            val title = cTitleInput.text.toString()
            val description = cDescriptionInput.text.toString()
            val status = 1
            Toast.makeText(this, "title: $title, description: $description", Toast.LENGTH_SHORT)
                .show()
            if (title.isEmpty() || description.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Create a Reminder object
            val reminder = ReminderModel(
                title = title,
                description = description,
                status = status
            )
            // Insert Reminder to DB
            val responseId: Long = dbHelper!!.insertReminder(reminder)
            // Show Toast
            Toast.makeText(this, "Response ID: $responseId", Toast.LENGTH_SHORT).show()

            if (responseId != -1L) {
                Toast.makeText(this, "Reminder Created", Toast.LENGTH_SHORT).show()
                // Pop
                finish()
            } else {
                Toast.makeText(this, "Reminder Not Created", Toast.LENGTH_SHORT).show()
            }

            // Navigate to MainActivity
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }
    }

    /**
     * Inserting new reminder to in db and refreshing the list
     */
//    private fun createReminder(reminder: ReminderModel) {
//        val id = dbHelper!!.insertReminder(reminder)    // inserting reminder in db and getting newly inserted reminder id
//        val new = dbHelper!!.getReminder(id.toInt())  // get the newly inserted reminder from db
//        if (new != null) {
//            allReminders.add(0, new)    // adding new reminder to array list at 0 position
//            myAdapter!!.notifyDataSetChanged()  // refreshing the list
//        }
//    }

    fun filterRecyclerView() {
        // 1- RecyclerView
        val filterRecyclerView: RecyclerView = findViewById(R.id.recyclerView_filter)
        filterRecyclerView.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)

        // 2- Data source: List of ReminderModel Objects
        var filtersList: ArrayList<FilterModel> = ArrayList()

        var filter1 = FilterModel(1, "Mon", true)
        var filter2 = FilterModel(2, "Tue", false)
        var filter3 = FilterModel(3, "Wed", false)
        var filter4 = FilterModel(4, "Thu", false)
        var filter5 = FilterModel(5, "Fri", false)
        var filter6 = FilterModel(6, "Sat", false)
        var filter7 = FilterModel(7, "Sun", false)

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