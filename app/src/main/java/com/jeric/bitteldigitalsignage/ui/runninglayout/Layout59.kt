package com.jeric.bitteldigitalsignage.ui.runninglayout

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.os.Handler
import android.os.Message
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.jeric.bitteldigitalsignage.R
import com.jeric.bitteldigitalsignage.datastore.model.STB
import com.jeric.bitteldigitalsignage.network.domain.model.EventFeedModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class Layout59 @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {
    private val mHandler: ScrollHandler
    private val mAdapter: MyAdapter
    private val recyclerView: RecyclerView
    override fun dispatchTouchEvent(ev: MotionEvent): Boolean {
        return false
    }
    fun layout59(data: List<EventFeedModel>,media: String) {
        val name = findViewById<TextView>(R.id.tv_title)
        mAdapter.setList(data)
        if (data.isNotEmpty()) {
            mHandler.sendEmptyMessageDelayed(0, 100)
            name?.text= media
        }
    }
    fun smoothScroll() {
        recyclerView.smoothScrollBy(0, 5)
        mHandler.sendEmptyMessageDelayed(0, 100)
    }
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mHandler.removeCallbacksAndMessages(null)
    }
    private class ScrollHandler(mView: Layout59?) : Handler() {
        private val view: WeakReference<Layout59?> = WeakReference(mView)
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            if (view.get() != null) {
                view.get()!!.smoothScroll()
            }
        }
    }
    private class MyAdapter : RecyclerView.Adapter<ViewHolder>() {
        private val list: MutableList<EventFeedModel> = ArrayList()

        @SuppressLint("NotifyDataSetChanged")
        fun setList(list: List<EventFeedModel>) {
            this.list.clear()
            this.list.addAll(list)
            notifyDataSetChanged()
        }
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.feed_item_059, parent, false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (position % 7 == 0) {
                holder.tvCompanyColor.setTextColor(Color.parseColor("#00b592"))
                holder.view.setBackgroundColor(Color.parseColor("#00b592"))
                holder.txtBackground.setBackgroundResource(R.drawable.rounded_corners)
            } else if (position % 7 == 1) {
                holder.tvCompanyColor.setTextColor(Color.parseColor("#cc14e8"))
                holder.view.setBackgroundColor(Color.parseColor("#cc14e8"))
                holder.txtBackground.setBackgroundResource(R.drawable.rounded_corners2)
            } else if (position % 7 == 2) {
                holder.tvCompanyColor.setTextColor(Color.parseColor("#eb1314"))
                holder.view.setBackgroundColor(Color.parseColor("#eb1314"))
                holder.txtBackground.setBackgroundResource(R.drawable.rounded_corners3)
            } else if (position % 7 == 3) {
                holder.tvCompanyColor.setTextColor(Color.parseColor("#1c4dcd"))
                holder.view.setBackgroundColor(Color.parseColor("#1c4dcd"))
                holder.txtBackground.setBackgroundResource(R.drawable.rounded_corners4)
            } else if (position % 7 == 4) {
                holder.tvCompanyColor.setTextColor(Color.parseColor("#419e1c"))
                holder.view.setBackgroundColor(Color.parseColor("#419e1c"))
                holder.txtBackground.setBackgroundResource(R.drawable.rounded_corners5)
            } else if (position % 7 == 5) {
                holder.tvCompanyColor.setTextColor(Color.parseColor("#dba800"))
                holder.view.setBackgroundColor(Color.parseColor("#dba800"))
                holder.txtBackground.setBackgroundResource(R.drawable.rounded_corners6)
            }
            holder.bind(list[position % list.size])
        }
        override fun getItemCount(): Int {
            return if (list.size > 0) Int.MAX_VALUE else 0
        }
    }
    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvCompanyColor = itemView.findViewById<TextView>(R.id.tv_company)!!
        val view = itemView.findViewById<View>(R.id.lineColor)!!
        val txtBackground = itemView.findViewById<TextView>(R.id.tv_schedule)!!
        @SuppressLint("SimpleDateFormat")
        fun bind(data: EventFeedModel) {
            CoroutineScope(Dispatchers.Main).launch {
                launch {
                    val logo = itemView.findViewById<ImageView>(R.id.iv_logo)
                    val tvCompany = itemView.findViewById<TextView>(R.id.tv_company)
                    val tvEvent = itemView.findViewById<TextView>(R.id.tv_description)
                    val tvLocation = itemView.findViewById<TextView>(R.id.tv_location)
                    val tvGroupEvent = itemView.findViewById<TextView>(R.id.tv_group_event)
                    val tvFloor = itemView.findViewById<TextView>(R.id.tv_floor)
                    val tvSchedule = itemView.findViewById<TextView>(R.id.tv_schedule)
                    val imageUri = "${STB.HOST}:${STB.PORT}/" + data.imgUri
                    logo?.load(imageUri) {
                        memoryCachePolicy(CachePolicy.DISABLED)
                    }
                    tvGroupEvent?.text = data.owner
                    tvCompany?.text = data.owner
                    tvEvent?.text = data.description
                    tvLocation?.text = data.location
                    tvFloor?.text = ""
                    if (data.start?.isNotEmpty() == true && data.end?.isNotEmpty() == true) {
                        val format1 = SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
                        val dateFormat3: DateFormat = SimpleDateFormat("MMM dd")
                        val scheduleStartDate = format1.parse(data.start)
                        val start = dateFormat3.format(scheduleStartDate!!)
                        tvSchedule?.text = start
                    }
                }
            }
        }
    }
    init {
        inflate(context, R.layout.feed_059, this)
        mHandler = ScrollHandler(this)
        mAdapter = MyAdapter()
        recyclerView = findViewById<View>(R.id.rv_feed59) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mAdapter
    }
}
