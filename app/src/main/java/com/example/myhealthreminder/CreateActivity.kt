package com.example.myhealthreminder

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myhealthreminder.Adapters.FiltersAdapter
import com.example.myhealthreminder.Models.FilterModel

class CreateActivity : AppCompatActivity() {
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

        filterRecyclerView()

        val createBtn: Button = findViewById(R.id.cCreateBtn);
        createBtn.setOnClickListener() {
            // Pop
            finish()

            // Navigate to MainActivity
//            val intent = Intent(this, MainActivity::class.java)
//            startActivity(intent)
        }
    }
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