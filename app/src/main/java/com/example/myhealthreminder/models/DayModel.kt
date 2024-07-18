package com.example.myhealthreminder.models

import java.io.Serializable

data class DayModel(val id: Int, var title: String, var status: Boolean, var fullDay: String = "") :
    Serializable

// create public ArrayList of
// DayModel class
 var dayList = arrayListOf(
     DayModel(1, "Mon", false),
     DayModel(2, "Tue", false),
     DayModel(3, "Wed", false),
     DayModel(4, "Thu", false),
     DayModel(5, "Fri", false),
     DayModel(6, "Sat", false),
     DayModel(7, "Sun", false)
 )

// for loop to populate ArrayList
// with DayModel objects


// add DayModel to ArrayList

//dayList.add(DayModel(1, "Day 1", false))
//dayList.add(DayModel(2, "Day 2", false))
//dayList.add(DayModel(3, "Day 3", false))
//DayModel(1, "Mon", false),
//DayModel(2, "Tue", false),
//DayModel(3, "Wed", false),
//DayModel(4, "Thu", false),
//DayModel(5, "Fri", false),
//DayModel(6, "Sat", false),
//DayModel(7, "Sun", false)

/* add DayModel to ArrayList using for loop */





