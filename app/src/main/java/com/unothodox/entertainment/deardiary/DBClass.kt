package com.unothodox.entertainment.deardiary

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBClass(context: Context) : SQLiteOpenHelper(
        context, DBName, null, DBVersion)  {

    companion object {
        const val DBName = "dearDiary.db"
        const val DBVersion = 1

        const val TableName = "ENTRIES"
        const val ColumnID = "ID"
        const val ColumnTitle = "TITLE"
        const val ColumnDate = "DATE"
        const val ColumnTime = "TIME"
        const val ColumnTopic = "TOPIC"
        const val ColumnContent = "CONTENT"

        const val TableName2 = "TOPICS"
    }

    override fun onCreate(database: SQLiteDatabase?) {
        val createTable = "CREATE TABLE $TableName ( " +
                "$ColumnID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$ColumnTitle TEXT, " +
                "$ColumnDate TEXT, " +
                "$ColumnTime TEXT, " +
                "$ColumnTopic TEXT, " +
                "$ColumnContent TEXT)"
        database?.execSQL(createTable)

        val createTable2 = "CREATE TABLE $TableName2 ( " +
                "$ColumnID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "$ColumnTopic TEXT)"
        database?.execSQL(createTable2)
    }

    override fun onUpgrade(database: SQLiteDatabase?, p1: Int, p2: Int) {

    }

    fun addEntry(entry: EntryObject)    {
        val database = this.writableDatabase
        val cv = ContentValues().apply {
            put(ColumnTitle, entry.title)
            put(ColumnDate, entry.date)
            put(ColumnTime, entry.time)
            put(ColumnTopic, entry.topic)
            put(ColumnContent, entry.content)
        }
        database.insert(TableName, null, cv)

        if (!doesExistInTable2(entry.topic) && entry.topic != "") {
            val cv2 = ContentValues().apply {
                put(ColumnTopic, entry.topic)
            }
            database.insert(TableName2, null, cv2)
        }
    }

    fun getEntries() : Array<EntryObject> {
        val database = this.readableDatabase
        val cursor = database.query(
                TableName,
                arrayOf(ColumnID, ColumnTitle, ColumnDate, ColumnContent),
                null,
                null,
                null,
                null,
                null
        )
        val returnValues = mutableListOf<EntryObject>()
        with(cursor)    {
            while (moveToNext())
                returnValues.add(EntryObject().apply {
                    id = getInt(getColumnIndexOrThrow(ColumnID))
                    title = getString(getColumnIndexOrThrow(ColumnTitle))
                    date = getString(getColumnIndexOrThrow(ColumnDate))
                    content = getString(getColumnIndexOrThrow(ColumnContent))
                })
        }
        return returnValues.toTypedArray()
    }

    fun getEntryAt(ID: Int) : EntryObject {
        val database = this.readableDatabase
        val cursor = database.query(
                TableName,
                arrayOf(ColumnTitle, ColumnDate, ColumnTime, ColumnTopic, ColumnContent),
                "$ColumnID =?",
                arrayOf(ID.toString()),
                null,
                null,
                null
        )
        with(cursor)    {
            if (moveToFirst())
                return EntryObject().apply {
                    title = getString(getColumnIndexOrThrow(ColumnTitle))
                    date = getString(getColumnIndexOrThrow(ColumnDate))
                    time = getString(getColumnIndexOrThrow(ColumnTime))
                    topic = getString(getColumnIndexOrThrow(ColumnTopic))
                    content = getString(getColumnIndexOrThrow(ColumnContent))
                }
        }
        return EntryObject()
    } //useless

    private fun doesExistInTable2(topic: String) : Boolean   {
        val database = this.readableDatabase
        val cursor = database.query(
                TableName2,
                arrayOf(ColumnID),
                "$ColumnTopic =?",
                arrayOf(topic),
                null,
                null,
                null
        )
        with(cursor)    {
            return count == 1
        }
    }

    fun getTopics() : ArrayList<String> {
        val database = this.readableDatabase
        val cursor = database.query(
                TableName2,
                arrayOf(ColumnTopic),
                null,
                null,
                null,
                null,
                null
        )

        val returnValues = ArrayList<String>()
        with(cursor)    {
            while (moveToNext())
                returnValues.add(getString(getColumnIndexOrThrow(ColumnTopic)))
        }
        return returnValues
    }

    fun getEntriesOf(topic: String) : ArrayList<EntryObject> {
        val database = this.readableDatabase
        val cursor = database.query(
                TableName,
                arrayOf(ColumnID, ColumnTitle, ColumnDate, ColumnTime, ColumnContent),
                "$ColumnTopic =?",
                arrayOf(topic),
                null,
                null,
                null
        )
        val returnValues = ArrayList<EntryObject>()
        with(cursor)    {
            while (moveToNext())
                returnValues.add(EntryObject().apply {
                    id = getInt(getColumnIndexOrThrow(ColumnID))
                    title = getString(getColumnIndexOrThrow(ColumnTitle))
                    date = getString(getColumnIndexOrThrow(ColumnDate))
                    time = getString(getColumnIndexOrThrow(ColumnTime))
                    content = getString(getColumnIndexOrThrow(ColumnContent))
                })
        }
        return returnValues
    }

    fun getEntriesAt(date: String) : ArrayList<EntryObject> {
        val database = this.readableDatabase
        val cursor = database.query(
                TableName,
                arrayOf(ColumnID, ColumnTitle, ColumnTopic, ColumnTime, ColumnContent),
                "$ColumnDate =?",
                arrayOf(date),
                null,
                null,
                null
        )
        val returnValues = ArrayList<EntryObject>()
        with(cursor)    {
            while (moveToNext())
                returnValues.add(EntryObject().apply {
                    id = getInt(getColumnIndexOrThrow(ColumnID))
                    title = getString(getColumnIndexOrThrow(ColumnTitle))
                    topic = getString(getColumnIndexOrThrow(ColumnTopic))
                    time = getString(getColumnIndexOrThrow(ColumnTime))
                    content = getString(getColumnIndexOrThrow(ColumnContent))
                })
        }
        return returnValues
    }

    fun getDates() : ArrayList<DateObject>  {
        val database = this.readableDatabase
        val cursor = database.query(
                TableName,
                arrayOf(ColumnDate),
                null,
                null,
                null,
                null,
                null
        )
        val returnArray = ArrayList<DateObject>()
        val dateArray = ArrayList<String>()
        with(cursor)    {
            while (moveToNext())    {
                val date = getString(getColumnIndexOrThrow(ColumnDate))
                if (!dateArray.contains(date)) {
                    dateArray.add(date)
                    val subs = getSubs(date, 3)
                    returnArray.add(DateObject().apply {
                        this.date = date
                        sub0 = subs[0]
                        sub1 = subs[1]
                        sub2 = subs[2]
                    })
                }
            }
        }
        return  returnArray
    }

    private fun getSubs(date: String, num: Int) : ArrayList<String>    {
        val database = this.readableDatabase
        val cursor = database.query(
                TableName,
                arrayOf(ColumnTopic, ColumnTitle),
                "$ColumnDate =?",
                arrayOf(date),
                null,
                null,
                null
        )
        val subs = ArrayList<String>()
        //todo - avoid repetition in subs
        with(cursor)    {
            for (i in 0 until num)   {
                if (moveToPosition(i))   {
                    val topic = getString(getColumnIndexOrThrow(ColumnTopic))
                    if (topic != "")
                        subs.add(topic)
                    else
                        subs.add(getString(getColumnIndexOrThrow(ColumnTitle)))
                }else   {
                    subs.add("")
                }
            }
        }
        return subs
    }
}
