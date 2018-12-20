package com.unothodox.entertainment.deardiary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import java.text.DateFormat

class EntryActivity : AppCompatActivity() {

    private lateinit var myDB :DBClass

    private lateinit var tvTitle: TextView
    private lateinit var tvDate: TextView
    private lateinit var tvTime: TextView
    private lateinit var tvTopic: TextView
    private lateinit var tvContent: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entry)

        myDB = DBClass(this)

        tvTitle = findViewById(R.id.tv_title)
        tvDate = findViewById(R.id.tv_date)
        tvTime = findViewById(R.id.tv_time)
        tvTopic = findViewById(R.id.tv_topic)
        tvContent = findViewById(R.id.tv_content)

        val id = intent.extras["ID"] as Int
        val e = myDB.getEntryAt(id)

        tvTitle.text = e.title
        tvDate.text = DateFormat.getDateInstance()
                .format(DateFormat.getDateInstance(DateFormat.SHORT)
                        .parse(e.date))
        tvTime.text = DateFormat.getTimeInstance()
                .format(DateFormat.getTimeInstance(DateFormat.SHORT)
                        .parse(e.time))
        tvTopic.text = e.topic
        tvContent.text = e.content
    }
}
