package com.unothodox.entertainment.deardiary

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.fragment_date_book.view.*
import kotlinx.android.synthetic.main.fragment_topics.view.*
import java.lang.Error

class HomeActivity : AppCompatActivity() {

    private lateinit var pagerAdapter: PagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        pagerAdapter = PagerAdapter(supportFragmentManager)
        vp_home.adapter = pagerAdapter

        tv_events.setOnClickListener {
            vp_home.currentItem = 0
        }
        tv_date_book.setOnClickListener {
            vp_home.currentItem = 1
        }

        vp_home.addOnPageChangeListener(
                object: ViewPager.OnPageChangeListener {
                    override fun onPageSelected(position: Int) {
                        when(position)  {
                            0 -> {
                                tv_events.setBackgroundResource(
                                        R.color.colorSelectedTrue)
                                tv_date_book.setBackgroundResource(
                                        R.color.colorSelectedFalse)
                            }
                            1 -> {
                                tv_events.setBackgroundResource(
                                        R.color.colorSelectedFalse)
                                tv_date_book.setBackgroundResource(
                                        R.color.colorSelectedTrue)
                            }
                        }
                    }

                    override fun onPageScrollStateChanged(state: Int) { }

                    override fun onPageScrolled(position: Int,
                                                positionOffset: Float,
                                                positionOffsetPixels: Int) { }
                })
    }

    class PagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getCount(): Int = 2

        override fun getItem(position: Int): Fragment {
            val fragment = PagerFragment()
            fragment.arguments = Bundle().apply {
                putInt("POS", position)
            }
            return fragment
        }

        override fun getItemPosition(`object`: Any): Int =
                android.support.v4.view.PagerAdapter.POSITION_NONE
    }

    class PagerFragment : Fragment()    {

        override fun onCreateView(inflater: LayoutInflater,
                                  container: ViewGroup?,
                                  savedInstanceState: Bundle?): View {
            val position = arguments?.get("POS")
            val context = activity as Context
            val myDB = DBClass(context)

            Log.d("onCreate", "called...")

            return when (position) {
                0 -> {
                    val view = inflater.inflate(R.layout.fragment_topics,
                            container, false)
                    val layoutManager = GridLayoutManager(context, 2)
                    view.rv_topics.layoutManager = layoutManager
                    val topicList = myDB.getTopics().apply {
                        add(0, "Add New")
                    }
                    val adapter = TopicAdapter(context, topicList)
                    view.rv_topics.adapter = adapter

                    view
                }
                1 -> {
                    val view = inflater.inflate(R.layout.fragment_date_book,
                            container, false)
                    val layoutManager = LinearLayoutManager(context)
                    view.rv_dates.layoutManager = layoutManager
                    //todo - || these things get left over...
                    val dates = myDB.getDates().apply {
                        add(0, DateObject())
                    }
                    val adapter = DateAdapter(context, dates)
                    view.rv_dates.adapter = adapter

                    view
                }
                else -> throw Error("Add another if thingy.")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        pagerAdapter.notifyDataSetChanged()
    }
}
