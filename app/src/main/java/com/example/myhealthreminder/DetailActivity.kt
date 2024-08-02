package com.example.myhealthreminder

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myhealthreminder.models.AlarmItemModel
import com.example.myhealthreminder.models.ReminderModel
import com.example.myhealthreminder.utils.DataBaseHelper
import com.example.myhealthreminder.utils.cancelAllAlarmTimes
import com.example.myhealthreminder.utils.convertTimeFormat
import com.example.myhealthreminder.utils.setAllAlarmTimes

class DetailActivity : AppCompatActivity() {
    private var TAG = "DetailActivity"
    private var dbHelper: DataBaseHelper? = null
    lateinit var reminderData: ReminderModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.constraintLayout)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // enable the back button in the navigation bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        val imageView: ImageView = findViewById(R.id.imageView)
        val dTitle: TextView = findViewById(R.id.dTitle)
        val dDescription: TextView = findViewById(R.id.dDescriptionData)
        val dDay: TextView = findViewById(R.id.dDayData)
        val dTime: TextView = findViewById(R.id.dTimeData)
        val dDeleteBtn: Button = findViewById(R.id.dDeleteBtn)

        // Get the data from the intent
        val intent = intent
        val bundle = intent.extras
        reminderData = intent.getSerializableExtra("reminderData") as ReminderModel

        if (bundle != null) {
            // Get the data from the intent
            imageView.setImageResource(bundle.getInt("image"))
            dTitle.text = (reminderData.title)
            dDescription.text = (reminderData.description)
            dDay.text = (reminderData.reminderDays)
            dTime.text = convertTimeFormat(reminderData.reminderTimes)
        }

        supportActionBar!!.title =
            "Detail" // ${if (reminderData.status == 1) "active" else "inactive"}

        // Initialize DBHelper
        dbHelper = DataBaseHelper(this, null, null, 1)
        dDeleteBtn.setOnClickListener {
            deleteConfirmation(reminderData)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        val item = menu!!.findItem(R.id.menuStatus)
        item.title = if (reminderData.status == 1) "Deactivate" else "Activate"
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuStatus -> {
                switchStatus(reminderData)
//                Toast.makeText(this, "Update Status", Toast.LENGTH_SHORT).show()
            }

            R.id.menuEdit -> {
                val intent = Intent(this, CreateActivity::class.java).apply {
                    putExtra("reminderData", reminderData)
                }
                this.startActivity(intent)
                //            Toast.makeText(this, "Edit", Toast.LENGTH_SHORT).show()
            }

            R.id.menuDelete -> {
                deleteConfirmation(reminderData)
                //            Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show()
            }

            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }

    private fun switchStatus(reminder: ReminderModel) {
        val responseStatus = dbHelper!!.updateReminderStatus(reminder)

        if (responseStatus > 0) {
            // get the updated reminder data from the database
            val responseData = dbHelper!!.getReminder(reminder.id)
//            Log.d(TAG, "responseData: $responseData")
//            Log.d(TAG, "reminderData: $reminderData")

            reminderData = responseData
            // check if the status is 1 or 0 and cancel or schedule the alarm
            val scheduler = AndroidAlarmScheduler(this)

            if (reminderData.status == 1) {
                // schedule the alarm
                setAllAlarmTimes(this, reminderData)
            }
            else {
                // Cancel the alarm
                cancelAllAlarmTimes(this, reminderData)
            }


            // Update the menu item title to reflect the new status value (active or inactive) in the menu
            invalidateOptionsMenu() // This will trigger the onCreateOptionsMenu method to be called again
            Toast.makeText(this, "Status updated successfully", Toast.LENGTH_SHORT).show()

        }
    }

    private fun deleteConfirmation(reminderData: ReminderModel) {
        val alertDialog = AlertDialog.Builder(this, R.style.AlertDialog)

        alertDialog.setTitle("Confirm Delete?")
        alertDialog.setMessage("Are you sure you want to delete this?")
        alertDialog.setIcon(R.drawable.baseline_delete_forever_24)

        alertDialog.setPositiveButton("YES", DialogInterface.OnClickListener { dialog, which ->
            val responseStatus = dbHelper!!.deleteReminder(reminderData)
            if (responseStatus > 0) {
//                Log.d(TAG, "deleteConfirmation: $responseStatus")
                finish()
            }
        })

        alertDialog.setNegativeButton("NO", DialogInterface.OnClickListener { dialog, which ->
            dialog.cancel() //Cancel the dialog
        })
        alertDialog.show()
    }
}