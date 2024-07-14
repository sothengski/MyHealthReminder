package com.example.myhealthreminder

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myhealthreminder.models.ReminderModel
import com.example.myhealthreminder.utils.DataBaseHelper

class DetailActivity : AppCompatActivity() {
    private var TAG = "DetailActivity"
    private var dbHelper: DataBaseHelper? = null

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
        supportActionBar!!.title = "Detail"

        val imageView: ImageView = findViewById(R.id.imageView)
        val dTitle: TextView = findViewById(R.id.dTitle)
        val dDescription: TextView = findViewById(R.id.dDescriptionData)
        val dDay: TextView = findViewById(R.id.dDayData)
        val dTime: TextView = findViewById(R.id.dTimeData)
        val dDeleteBtn: Button = findViewById(R.id.dDeleteBtn)

        // Get the data from the intent
        val intent = intent
        val bundle = intent.extras
        val reminderData = intent.getSerializableExtra("reminderData") as ReminderModel

        if (bundle != null) {
            // Get the data from the intent
            imageView.setImageResource(bundle.getInt("image"))
            dTitle.text = (reminderData.title)
            dDescription.text = (reminderData.description)
            dDay.text = (reminderData.reminderDays)
            dTime.text = (reminderData.reminderTimes)
        }
        // Initialize DBHelper
        dbHelper = DataBaseHelper(this, null, null, 1)
        dDeleteBtn.setOnClickListener {
            deleteConfirmation(reminderData)
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