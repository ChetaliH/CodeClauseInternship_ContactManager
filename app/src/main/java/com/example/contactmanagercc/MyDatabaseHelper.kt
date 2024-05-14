package com.example.contactmanagercc

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class MyDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "my_database.db"
        const val DATABASE_VERSION = 1

        // Define table and column names
        const val TABLE_NAME = "contacts"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_PHONE_NUMBER = "phone_number"
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Create the table
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_PHONE_NUMBER TEXT
            )
        """.trimIndent()
        db.execSQL(createTableQuery)

        insertContact("John Doe", "1234567890")
        insertContact("Jane Smith", "9876543210")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Handle database schema changes if needed
        // This method is called when the database version is increased
    }

    fun insertContact(name: String, phoneNumber: String) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NAME, name)
            put(COLUMN_PHONE_NUMBER, phoneNumber)
        }
        db.insert(TABLE_NAME, null, contentValues)
    }

    fun getAllContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        cursor.use {
            // Check if cursor has rows
            if (it.moveToFirst()) {
                do {
                    val idIndex = it.getColumnIndex(COLUMN_ID)
                    val nameIndex = it.getColumnIndex(COLUMN_NAME)
                    val phoneNumberIndex = it.getColumnIndex(COLUMN_PHONE_NUMBER)

                    // Check if column indexes are valid
                    if (idIndex != -1 && nameIndex != -1 && phoneNumberIndex != -1) {
                        val id = it.getInt(idIndex)
                        val name = it.getString(nameIndex)
                        val phoneNumber = it.getString(phoneNumberIndex)
                        val contact = Contact(id, name, phoneNumber)
                        contacts.add(contact)
                    } else {
                        // Log error or handle the situation where columns are not found
                        // This helps in debugging if the column names are incorrect
                    }
                } while (it.moveToNext())
            }
        }
        return contacts
    }

}

data class Contact(
    val id: Int,
    val name: String,
    val phoneNumber: String
)
