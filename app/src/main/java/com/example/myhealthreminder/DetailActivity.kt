package com.example.myhealthreminder

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.myhealthreminder.models.ReminderModel

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.img)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // enable the back button in the navigation bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.title = "Detail"

        val imageView: ImageView = findViewById(R.id.imageView)
        val dTitle: TextView = findViewById(R.id.dTitle)
        val dDescription: TextView = findViewById(R.id.dDescription)

        // Get the data from the intent
        val intent = intent
        val bundle = intent.extras
        val reminderData = intent.getSerializableExtra("reminderData") as ReminderModel

        if (bundle != null) {
            // Get the data from the intent
            imageView.setImageResource(bundle.getInt("image"))
            dTitle.text = (reminderData.title)
            dDescription.text = (reminderData.description)
        }
    }
}