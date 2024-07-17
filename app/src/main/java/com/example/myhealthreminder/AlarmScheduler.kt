package com.example.myhealthreminder

import com.example.myhealthreminder.models.AlarmItemModel

interface AlarmScheduler {
    fun schedule(item : AlarmItemModel)
    fun cancel(item : AlarmItemModel)
}