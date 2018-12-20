package com.unothodox.entertainment.deardiary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list.*
import java.text.DateFormat
import java.util.*
import kotlin.collections.ArrayList

class ListActivity : AppCompatActivity() {

    private lateinit var myDB : DBClass

    private var listFrom : Int = -1
    private lateinit var listTitle : String
    private lateinit var array : ArrayList<EntryObject>

    private lateinit var layoutManager : RecyclerView.LayoutManager
    private lateinit var adapter : RecyclerView.Adapter<*>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        myDB = DBClass(this)

        listFrom = intent.extras["FROM"] as Int
        listTitle = intent.extras["TITLE"] as String
        tv_list_title.text =
                if  (listFrom == 0) listTitle
                else
                    DateFormat.getDateInstance()
                            .format(DateFormat.getDateInstance(DateFormat.SHORT)
                                    .parse(listTitle))
        //todo - do the split talk app...

        layoutManager = LinearLayoutManager(this)
        rv_list.layoutManager = layoutManager
        adapter = if (listFrom == 0)  {
            array = myDB.getEntriesOf(listTitle).apply {
                add(0, EntryObject())
            }
            ListAdapter(this, array, listTitle)

        }else   {
            array =
                    if (listTitle == DateFormat.getDateInstance(DateFormat.SHORT)
                                    .format(Date()))
                        myDB.getEntriesAt(listTitle).apply {
                            add(0, EntryObject())
                        }
                    else
                        myDB.getEntriesAt(listTitle)
            ListAdapter(this, array, "")
        }
        rv_list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()

        if (array.size != 0) {
            val arrayCopy =
                    if (listFrom == 0) {
                        myDB.getEntriesOf(listTitle).apply {
                            add(0, EntryObject())
                        }
                    } else {
                        if (listTitle == DateFormat.getDateInstance(DateFormat.SHORT)
                                        .format(Date()))
                            myDB.getEntriesAt(listTitle).apply {
                                add(0, EntryObject())
                            }
                        else
                            myDB.getEntriesAt(listTitle)
                    }
            if (array.size != arrayCopy.size) {
                array = arrayCopy
                adapter.notifyDataSetChanged()
            }
        }
    }
}
