package com.example.myhealthreminder

import android.app.TimePickerDialog
import android.content.Intent
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
import com.example.myhealthreminder.utils.convertTimeFormat

class CreateActivity : AppCompatActivity() {

    private var TAG: String = "CreateActivity"

    private var dbHelper: DataBaseHelper? = null
    var daysList: ArrayList<DayModel> = ArrayList()

    var daysSelected = ""

    var isUpdate: Boolean = false

    lateinit var reminderData: ReminderModel
    var reminderTime: String = ""


//    lateinit var btnRemiderTime: Button

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
//        supportActionBar!!.setHomeAsUpIndicator(R.drawable.back_arrow)

        val cTitleInput: EditText = findViewById(R.id.cTitleInput)
        val cDescriptionInput: EditText = findViewById(R.id.cDescriptionInput)
        val btnRemiderTime: Button = findViewById(R.id.btnRemiderTime)
        val createBtn: Button = findViewById(R.id.cCreateBtn)

        // Get the data from the intent
        val intent = intent
        val bundle = intent.extras

        if (bundle != null) {
            reminderData = intent.getSerializableExtra("reminderData") as ReminderModel
            isUpdate = true
            // Get the data from the intent

//            imageView.setImageResource(bundle.getInt("image"))
            cTitleInput.setText(reminderData.title)
            cDescriptionInput.setText(reminderData.description)
            reminderTime =  reminderData.reminderTimes
            btnRemiderTime.text = convertTimeFormat(reminderTime)
        }

        supportActionBar!!.title =
            if (isUpdate) getString(R.string.edit_reminder) else getString(R.string.add_reminder)
        createBtn.text = if (isUpdate) "Edit Reminder ID: ${reminderData.id}" else "Add Reminder"

        // Initialize DBHelper
        dbHelper = DataBaseHelper(this, null, null, 1)

        daysRecyclerView()

        // Set click listener for the button
            btnRemiderTime.setOnClickListener {
//                showTimePickerDialog()
                val calendar = Calendar.getInstance()
                val hour = calendar.get(Calendar.HOUR_OF_DAY)
                val minute = calendar.get(Calendar.MINUTE)

                val is24HourFormat = android.text.format.DateFormat.is24HourFormat(this)

                val timeSetListener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
                    val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
//                    Toast.makeText(this, "Selected Time: $selectedTime", Toast.LENGTH_SHORT).show()
                    reminderTime = selectedTime
                    btnRemiderTime.text = convertTimeFormat(reminderTime)
                }

                TimePickerDialog(this, timeSetListener, hour, minute, is24HourFormat).show()
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

            val timepicker: String = reminderTime
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
                reminderData = ReminderModel(
                    id = if (isUpdate) reminderData.id else 1,
                    title = title,
                    description = description,
                    status = status,
                    reminderDays = daysSelected,
                    reminderTimes = timepicker
                )
                // Edit an existing Reminder Object
                if (isUpdate) {
                    val responseId: Int = dbHelper!!.updateReminder(reminderData)
//                    Log.d("TAG", "responseId: ${responseId}")
                    if (responseId > 0) {
                        Toast.makeText(this, "Reminder Edit", Toast.LENGTH_SHORT).show()
                        // Pop
//                        finish()
                        val intent = Intent(this, MainActivity::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                        startActivity(intent)
                    } else {
                        Toast.makeText(this, "Reminder Can't Edit", Toast.LENGTH_SHORT).show()
                    }
                }
                // Create a Reminder object
                else if (!isUpdate) {

                    // Insert Reminder to DB
                    val responseId: Long = dbHelper!!.insertReminder(reminderData)

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
    }

//    fun convertTimeFormat(time: String, fromFormat: String= "HH:mm", toFormat: String= "hh:mm a"): String {
//        val sdf = SimpleDateFormat(fromFormat)
//        val date = sdf.parse(time)
//        sdf.applyPattern(toFormat)
//        return sdf.format(date)
//    }

//    fun displayTimeBasedOnUserSettings(selectedTime: String): String{
//        val calendar = Calendar.getInstance()
//        val hour = calendar.get(Calendar.HOUR_OF_DAY)
//        val minute = calendar.get(Calendar.MINUTE)
//
//        val is24HourFormat = android.text.format.DateFormat.is24HourFormat(this)
//        val formattedTime = if (is24HourFormat) {
//            String.format("%02d:%02d", hour, minute)
//        } else {
//            val hourFormatted = if (hour == 0 || hour == 12) 12 else hour % 12
//            val amPm = if (hour < 12) "AM" else "PM"
//            String.format("%02d:%02d %s", hourFormatted, minute, amPm)
//        }
//        return formattedTime
//    }

    private fun showTimePickerDialog() {
        val calendar = Calendar.getInstance()
        val hour = calendar.get(Calendar.HOUR_OF_DAY)
        val minute = calendar.get(Calendar.MINUTE)

        val is24HourFormat = android.text.format.DateFormat.is24HourFormat(this)

        val timeSetListener = TimePickerDialog.OnTimeSetListener { _, selectedHour, selectedMinute ->
            val selectedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
            Toast.makeText(this, "Selected Time: $selectedTime", Toast.LENGTH_SHORT).show()
//            btnRemiderTime.text = String.format("%02d:%02d", hour, minute)
        }

        TimePickerDialog(this, timeSetListener, hour, minute, is24HourFormat).show()
    }

//    private fun displayTimeBasedOnUserSettings(tvSelectedTime: TextView, hour: Int, minute: Int) {
//        val is24HourFormat = DateFormat.is24HourFormat(this)
//        val formattedTime = if (is24HourFormat) {
//            String.format("%02d:%02d", hour, minute)
//        } else {
//            val hourFormatted = if (hour == 0 || hour == 12) 12 else hour % 12
//            val amPm = if (hour < 12) "AM" else "PM"
//            String.format("%02d:%02d %s", hourFormatted, minute, amPm)
//        }
//        tvSelectedTime.text = "Selected Time: $formattedTime"
//    }

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