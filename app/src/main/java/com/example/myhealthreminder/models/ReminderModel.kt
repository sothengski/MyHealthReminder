package com.example.myhealthreminder.models

import java.io.Serializable

data class ReminderModel(
    var id: Int = 1,
    var title: String = "",
    var description: String = "",
    var status: Int = 0,
    var type: String = "",
    var img: String = "",
    var amount: String = "",
    var capSize: String = "",
    var reminderDays: String = "",
    var reminderTimes: String = "",
    var foodAndPills: String = "",
    var snoozeDuration: Int = 0,
    var reminderSound: String = "",
    var timestamp: String = ""
): Serializable

