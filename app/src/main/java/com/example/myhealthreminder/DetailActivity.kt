package com.example.myhealthreminder

import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toolbar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // enable the back button in the navigation bar
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
    }










//        // calling the action bar
//        var actionBar = getSupportActionBar()
//        // showing the back button in action bar
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true)
//        }
//
//        // this event will enable the back
//        // function to the button on press
//        override fun onContextItemSelected(item: MenuItem): Boolean {
//            when (item.itemId) {
//                android.R.id.home -> {
//                    finish()
//                    return true
//                }
//            }
//            return super.onContextItemSelected(item)
//        }




        val imageView: ImageView = findViewById(R.id.imageView);
        val dTitle: TextView = findViewById(R.id.dTitle);
        val dDescription: TextView = findViewById(R.id.dDescription);

//        Bundle bundle = getIntent().getExtras();
//        imageView.setImageResource(bundle.getInt("image"));
//        dTitle.setText(bundle.getString("title"));
//        dDescription.setText(bundle.getString("description"));
    }
}