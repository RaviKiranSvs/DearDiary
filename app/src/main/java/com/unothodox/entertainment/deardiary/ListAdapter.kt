package com.unothodox.entertainment.deardiary

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_list.view.*

class ListAdapter(private val context: Context,
                  private val e: ArrayList<EntryObject>,
                  private val topic: String) :
        RecyclerView.Adapter<ListAdapter.MyViewHolder>()    {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
                if (viewType == 0) LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_add, parent, false)
                else LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_list, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemViewType(position: Int) =
            if (e[position].time == "0")    0
            else    1

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (e[position].time != "0") {
            holder.view.tv_head.text =
                    if (topic == "") e[position].topic
                    else e[position].date
            holder.view.tv_sub.text = e[position].title
            holder.view.tv_time.text = e[position].time
            holder.view.tv_body.text = e[position].content

        }else   {
            holder.view.setOnClickListener {
                val intent = Intent(context, AddActivity::class.java).apply {
                    putExtra("TOPIC", topic)
                }
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = e.size
}