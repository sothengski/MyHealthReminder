package com.example.myhealthreminder.models

import java.io.Serializable

data class DayModel(val id: Int, var title: String, var status: Boolean, var fullDay: String = "") : Serializable
