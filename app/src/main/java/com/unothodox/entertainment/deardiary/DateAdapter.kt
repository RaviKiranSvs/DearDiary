package com.unothodox.entertainment.deardiary

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.layout_date.view.*
import java.text.DateFormat

class DateAdapter(private val context: Context,
                  private val dates: ArrayList<DateObject>) :
        RecyclerView.Adapter<DateAdapter.MyViewHolder>()    {

    class MyViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view =
                if (viewType == 0) LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_add, parent, false)
                else LayoutInflater.from(parent.context)
                        .inflate(R.layout.layout_date, parent, false)

        return MyViewHolder(view)
    }

    override fun getItemViewType(position: Int) = if (position == 0) 0 else 1

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        if (position != 0) {
            holder.view.tv_date.text = DateFormat.getDateInstance()
                    .format(DateFormat.getDateInstance(DateFormat.SHORT)
                            .parse(dates[position].date))
            holder.view.tv_sub_0.text = dates[position].sub0
            holder.view.tv_sub_1.text = dates[position].sub1
            holder.view.tv_sub_2.text = dates[position].sub2

            holder.view.setOnClickListener  {
                val intent = Intent(context, ListActivity::class.java).apply {
                    putExtra("FROM", 1)
                    putExtra("TITLE", dates[position].date)
                }
                context.startActivity(intent)
            }

        }else   {
            holder.view.setOnClickListener {
                val intent = Intent(context, AddActivity::class.java)
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = dates.size
}