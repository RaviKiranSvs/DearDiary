package com.unothodox.entertainment.deardiary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_add.*
import java.text.DateFormat
import java.util.*

class AddActivity : AppCompatActivity() {

    private lateinit var myDB: DBClass

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)

        myDB = DBClass(this)

        val array = myDB.getTopics().apply {
                    add(0, "Add Topic")
                    add("New Topic")
                }
        val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,
                array)
        sp_topics.adapter = adapter

        if  (intent.extras != null) {
            val topic = intent.extras["TOPIC"]
            if (array.contains(topic))
                sp_topics.setSelection(array.indexOf(topic))
        }

        sp_topics.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if (position == sp_topics.count-1)   {
                    val params = ConstraintLayout.LayoutParams(0,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT)
                            .apply {
                                startToEnd = R.id.sp_topics
                                endToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                                topToBottom = R.id.et_title
                                marginStart = resources.getDimension(R.dimen.eight).toInt()
                                marginEnd = resources.getDimension(R.dimen.eight).toInt()
                            }
                    et_topic.layoutParams = params

                }else   {
                    val params = ConstraintLayout.LayoutParams(0,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT)
                            .apply {
                                startToEnd = ConstraintLayout.LayoutParams.PARENT_ID
                                topToBottom = R.id.et_title
                            }
                    et_topic.layoutParams = params
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}
        }

        b_add.setOnClickListener {
            val title = et_title.text.toString()
            val content = et_content.text.toString()
            val topic =
                    if (sp_topics.selectedItemPosition == sp_topics.count -1)
                        et_topic.text.toString()
                    else
                        sp_topics.selectedItem.toString()

            myDB.addEntry(EntryObject().apply {
                this.title = title
                this.content = content
                this.date = DateFormat.getDateInstance(DateFormat.SHORT).format(Date())
                this.time = DateFormat.getTimeInstance(DateFormat.SHORT).format(Date())
                this.topic = topic
            })
            finish()
        }
    }
}
