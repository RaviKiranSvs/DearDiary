package com.unothodox.entertainment.deardiary

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_topic.view.*

class TopicAdapter(private val context: Context,
                   private val topics: ArrayList<String>) :
        RecyclerView.Adapter<TopicAdapter.MyViewHolder>()    {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.layout_topic, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.view.tv_topic.text = topics[position]

        if (position == 0)
            holder.view.iv_topic.setImageResource(R.drawable.add)

        holder.view.setOnClickListener {
            if  (position != 0) {
                val intent = Intent(context, ListActivity::class.java).apply {
                    putExtra("FROM", 0)
                    putExtra("TITLE", topics[position])
                }
                context.startActivity(intent)
            }else   {
                val intent = Intent(context, AddActivity::class.java).apply {
                    putExtra("TOPIC", "New Topic")
                }
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = topics.size
}