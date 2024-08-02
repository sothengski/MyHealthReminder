package com.example.myhealthreminder

import com.example.myhealthreminder.models.AlarmItemModel

interface AlarmScheduler {
    fun schedule(item : AlarmItemModel, triggerTime: Long, alarmId: Int)
    fun cancel(item : AlarmItemModel)
}