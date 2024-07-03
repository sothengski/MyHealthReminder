package com.example.myhealthreminder.Models

data class ReminderModel(
    val id: Int,
    val title: String,
    val description: String,
    val status: Boolean,
    val img: Int,
    val amount: String,
    val capSize: String,
    val reminderDays: String,
    val reminderTimes: String,
    val foodAndPills: String,
    val snoozeDuration: Int,
    val reminderSound: String
)
