package com.example.myhealthreminder.models

data class AlarmItemModel(
    val id: Int = 0,
//    val timeInMillis: Long = 0L,
//    val daysOfWeek: BitSet = BitSet.valueOf(ByteArray(7)), // Use BitSet for efficient day storage
//    val daysOfWeek: List<String>,
    val daysOfWeek: List<DayModel> = emptyList(),
    val time: String,
    val message: String
)
