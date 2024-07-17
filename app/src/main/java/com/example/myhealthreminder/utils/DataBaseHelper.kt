package com.example.myhealthreminder.utils

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.myhealthreminder.models.ReminderModel

class DataBaseHelper(
    context: Context?,
    name: String?,
    factory: SQLiteDatabase.CursorFactory?,
    version: Int
) : SQLiteOpenHelper(context, DATABASE_NAME, factory, DATABASE_VERSION) {

    // create private SQLiteDatabase
//    private var db: SQLiteDatabase = writableDatabase

    companion object {
        private const val DATABASE_VERSION = 1  // Database Version
        private const val DATABASE_NAME = "reminder_db" // Database Name
    }

    // Database Table Name
    private val TABLE_NAME = "reminderTable"

    // Attributes for Tables
    private val COLUMN_ID = "id"
    private val COLUMN_TITLE = "title"
    private val COLUMN_DESCRIPTION = "description"
    private val COLUMN_STATUS = "status"
    private val COLUMN_TYPE = "type"
    private val COLUMN_IMAGE = "image"
    private val COLUMN_DAYS = "days"
    private val COLUMN_TIMES = "times"
    private val COLUMN_SNOOZE_DURATION = "snooze_duration"
    private val COLUMN_TIMESTAMP = "timestamp"

    // Create table SQL query
    private val CREATE_TABLE = (
            "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TITLE + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_STATUS + " BOOLEAN,"
                    + COLUMN_TYPE + " TEXT,"
                    + COLUMN_IMAGE + " INTEGER,"
                    + COLUMN_DAYS + " TEXT,"
                    + COLUMN_TIMES + " TEXT,"
                    + COLUMN_SNOOZE_DURATION + " INTEGER,"
                    + COLUMN_TIMESTAMP + " TIMESTAMP DEFAULT CURRENT_TIMESTAMP"
                    + ")")

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("PRAGMA foreign_keys=ON")
        db?.execSQL(CREATE_TABLE) // Create Table
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME") // Drop older table if existed
        onCreate(db)  // Create tables again
    }

    //insert Reminder
    fun insertReminder(reminder: ReminderModel): Long {
        val db =
            this.writableDatabase // Create and/or open a database that will be used for reading and writing.
        val contentValues =
            ContentValues() // Create a new map of values, where column names are the keys

        // id and timestamp will be inserted automatically.
        contentValues.put(COLUMN_TITLE, reminder.title)
        contentValues.put(COLUMN_DESCRIPTION, reminder.description)
        contentValues.put(COLUMN_STATUS, reminder.status)
        contentValues.put(COLUMN_TYPE, reminder.type)
        contentValues.put(COLUMN_IMAGE, reminder.img)
        contentValues.put(COLUMN_DAYS, reminder.reminderDays)
        contentValues.put(COLUMN_TIMES, reminder.reminderTimes)
        contentValues.put(COLUMN_SNOOZE_DURATION, reminder.snoozeDuration)
        val id = db.insert(TABLE_NAME, null, contentValues) // Insert Row
        db.close() // Close database connection

        return id // Return newly inserted row id
    }

    // update Reminder
    fun updateReminder(reminder: ReminderModel): Int {
        val db =
            this.writableDatabase // Create and/or open a database that will be used for reading and writing.
        val contentValues =
            ContentValues().apply {  // Create a new map of values, where column names are the keys
                put(COLUMN_TITLE, reminder.title)
                put(COLUMN_DESCRIPTION, reminder.description)
                put(COLUMN_STATUS, reminder.status)
                put(COLUMN_TYPE, reminder.type)
                put(COLUMN_IMAGE, reminder.img)
                put(COLUMN_DAYS, reminder.reminderDays)
                put(COLUMN_TIMES, reminder.reminderTimes)
                put(COLUMN_SNOOZE_DURATION, reminder.snoozeDuration)
            }
//        contentValues.put(COLUMN_TIMESTAMP, reminder.timestamp)

        val whereClause = "$COLUMN_ID = ?"//"$COLUMN_ID = ${reminder::id}"
        val whereArgs = arrayOf(reminder.id.toString())

        val success = db.update(TABLE_NAME, contentValues, whereClause, whereArgs) // Updating Row

        db.close() // Close database connection
        return success
    }

    // update Reminder status by id
    fun updateReminderStatus(reminder: ReminderModel): Int{
        // Create and/or open a database that will be used for reading and writing.
        val db = this.writableDatabase
        val contentValues = ContentValues()
        // if status is 1, set it to 0 and vice versa for reminder status
        contentValues.put(COLUMN_STATUS, if (reminder.status == 1) 0 else 1)
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(reminder.id.toString())
       val success = db.update(TABLE_NAME, contentValues, whereClause, whereArgs)

        db.close()
        return success
    }

    // delete a single reminder
    fun deleteReminder(reminder: ReminderModel): Int {
        val db =
            this.writableDatabase // Create and/or open a database that will be used for reading and writing.
        db.delete(TABLE_NAME, "$COLUMN_ID LIKE ?", arrayOf(reminder.id.toString()))
        db.close() // Close database connection
        return 1
    }

    // get a single reminder
    fun getReminder(id: Int): ReminderModel {
        val db =
            this.readableDatabase // Create and/or open a database that will be used for reading and writing.
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_ID=?",
            arrayOf(id.toString()),
            null,
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        // Create a Reminder object
        val reminder = ReminderModel(
            cursor!!.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS)),
            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAYS)),
            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMES)),
            cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SNOOZE_DURATION)),
            cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP))
        )
        cursor.close() // Close database connection
        return reminder
    }

    // get all reminders
    fun getAllReminders(): List<ReminderModel> {
        //
        val db =
            this.readableDatabase // Create and/or open a database that will be used for reading and writing.
        val reminders = ArrayList<ReminderModel>() // ArrayList of Reminders
        val selectQuery = "SELECT * FROM $TABLE_NAME" // Select All Query
//        val cursor = db.rawQuery(selectQuery, null)
        val cursor: Cursor = db.rawQuery(selectQuery, null)
        db.beginTransaction()
        try {
//            cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
            if (cursor.moveToFirst()) {
                do {
                    // declare a Reminder variable
//                    val reminder = ReminderModel()
//                    reminder.setId(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)))

                    val reminder1 = ReminderModel(
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAYS)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMES)),
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SNOOZE_DURATION)),
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP))
                    )

                    cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
                        .also { reminder1.id = it }
                    reminder1.title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
                    reminder1.description =
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DESCRIPTION))
                    reminder1.status = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
                    reminder1.type = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TYPE))
                    reminder1.img = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_IMAGE))
                    reminder1.reminderDays =
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DAYS))
                    reminder1.reminderTimes =
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMES))
                    reminder1.snoozeDuration =
                        cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_SNOOZE_DURATION))
                    reminder1.timestamp =
                        cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TIMESTAMP))

                    reminders.add(reminder1)
                } while (cursor.moveToNext())
            }
//            Log.d("TAG", "getAllReminders: ${reminders.size}")
//            db.setTransactionSuccessful()
            cursor.close()
            return reminders
        } finally {
            db.endTransaction()
            cursor.close()
        }
    }
}