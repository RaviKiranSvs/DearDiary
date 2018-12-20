package com.unothodox.entertainment.deardiary

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.GridLayout
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    //private val TAG = "MainActivity"

    private lateinit var myDB : DBClass

    private lateinit var tv01 : TextView
    private lateinit var tv02 : TextView
    private lateinit var rvDate : RecyclerView
    private lateinit var rvTopics : RecyclerView

    private lateinit var layoutManager : RecyclerView.LayoutManager
    private lateinit var adapter : RecyclerView.Adapter<*>
    private lateinit var layoutManager1 : RecyclerView.LayoutManager
    private lateinit var adapter1 : RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        myDB = DBClass(this)

        tv01 = findViewById(R.id.tv_01)
        tv02 = findViewById(R.id.tv_02)
        rvDate = findViewById(R.id.rv_date)
        rvTopics = findViewById(R.id.rv_topics)

        tv01.setOnClickListener(listener)
        tv02.setOnClickListener(listener)

        //setRecyclerView()
    }

    /*private fun setRecyclerView()   {
        layoutManager = LinearLayoutManager(this)
        rvDate.layoutManager = layoutManager
        adapter = DateAdapter(this, myDB.getEntries())
        rvDate.adapter = adapter

        layoutManager1 = GridLayoutManager(this, 2)
        rvTopics.layoutManager = layoutManager1
        adapter1 = TopicAdapter(this, myDB.getTopics())
        rvTopics.adapter = adapter1
    }*/

    private val listener : View.OnClickListener = View.OnClickListener { view ->
        if (view.tag == "1") {
            tv01.setBackgroundResource(R.drawable.selected_true)
            tv02.setBackgroundResource(R.drawable.selected_false)

        }else if (view.tag == "2")  {
            tv01.setBackgroundResource(R.drawable.selected_false)
            tv02.setBackgroundResource(R.drawable.selected_true)
        }
    }

    fun addEntry(view: View)    {
        if (view.tag == "add")  {
            val intent = Intent(this, AddActivity::class.java)
            startActivityForResult(intent, 12)
        }
    }
/*
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 12)
            if (resultCode == Activity.RESULT_CANCELED)
                setRecyclerView()
    }*/
}
