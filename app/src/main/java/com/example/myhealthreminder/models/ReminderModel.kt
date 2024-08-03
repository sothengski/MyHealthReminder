package com.example.myhealthreminder.models

import java.io.Serializable

data class ReminderModel(
    var id: Int = 1,
    var title: String = "",
    var description: String = "",
    var status: Int = 0,
    var type: String = "",
    var img: String = "",
    var reminderDays: String = "",
    var reminderTimes: String = "",
    var snoozeDuration: Int = 0,
    var timestamp: String = ""
) : Serializable