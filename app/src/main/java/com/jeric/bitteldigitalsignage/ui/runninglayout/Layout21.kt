package com.jeric.bitteldigitalsignage.ui.runninglayout

import android.annotation.SuppressLint
import android.content.Context
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

class Layout21 @JvmOverloads constructor(
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
    fun layout21(data: List<EventFeedModel>, media: String) {
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
    private class ScrollHandler(mView: Layout21?) : Handler() {
        private val view: WeakReference<Layout21?> = WeakReference(mView)
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
            val view: View = LayoutInflater.from(parent.context).inflate(R.layout.feed_item_021, parent, false)
            return ViewHolder(view)
        }
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.find
            holder.bind(list[position % list.size])
        }
        override fun getItemCount(): Int {
            return if (list.isNotEmpty()) Int.MAX_VALUE else 0
        }
    }
    private class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SimpleDateFormat")
        fun bind(data: EventFeedModel) {
            CoroutineScope(Dispatchers.Main).launch {
                launch {
                    val logo = itemView.findViewById<ImageView>(R.id.iv_logo)
                    val tvCompany = itemView.findViewById<TextView>(R.id.tv_company)
                    val tvLocation = itemView.findViewById<TextView>(R.id.tv_location)
                    val tvGroupEvent = itemView.findViewById<TextView>(R.id.tv_group_event)
                    val tvFloor = itemView.findViewById<TextView>(R.id.tv_floor)
                    val tvStart = itemView.findViewById<TextView>(R.id.rv_start)
                    val tvEnd = itemView.findViewById<TextView>(R.id.tv_endtime)
                    val tvStat = itemView.findViewById<TextView>(R.id.tv_stat)
                    val tvStatus = itemView.findViewById<ImageView>(R.id.status)
                    val imageUri = "${STB.HOST}:${STB.PORT}/" + data.imgUri
                    logo?.load(imageUri) {
                        memoryCachePolicy(CachePolicy.DISABLED)
                    }
                    tvGroupEvent?.text = data.owner
                    tvCompany?.text = data.owner
                    tvLocation?.text = data.location
                    tvFloor?.text = ""
                    if (data.start?.isNotEmpty() == true && data.end?.isNotEmpty() == true) {
                        val format1 = SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
                        val dateFormat3: DateFormat = SimpleDateFormat("hh:mm a")
                        val scheduleStartDate = format1.parse(data.start)
                        val scheduleEndDate = format1.parse(data.end)
                        val currentEnd = format1.format(scheduleEndDate!!)
                        val currentStart = format1.format(scheduleStartDate!!)
                        val start = dateFormat3.format(scheduleStartDate)
                        val end = dateFormat3.format(scheduleEndDate)
                        val schedStart = Calendar.getInstance()
                        schedStart.time = scheduleStartDate
                        val schedEnd = Calendar.getInstance()
                        schedEnd.time = scheduleEndDate
                        tvStat.text = find(currentStart, currentEnd)
                        tvStart.text = start
                        tvEnd.text = end
                        when(find(currentStart, currentEnd)){
                            "IN PROGRESS" -> {
                                tvStatus.load(R.drawable.timestat)
                            }
                            "INCOMING" -> {
                                tvStatus.load(R.drawable.incoming)
                            }
                            else -> {
                                tvStatus.load(R.drawable.check)
                            }
                        }
                    }
                }
            }
        }
        val find = fun(startDate: String, endDate: String): String{
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd kk:mm:ss", Locale.getDefault())
            val dateStart: Date? = simpleDateFormat.parse(startDate)
            val dateEnd: Date = simpleDateFormat.parse(endDate) as Date
            val currentDate = Date()
            return if(currentDate.after(dateStart) && currentDate.before(dateEnd)) {
                "IN PROGRESS"
            } else if (currentDate.before(dateStart)){
                "INCOMING"
            }else {
                "COMPLETE"
            }
        }

    }
    init {
        inflate(context, R.layout.feed_021, this)
        mHandler = ScrollHandler(this)
        mAdapter = MyAdapter()
        recyclerView = findViewById<View>(R.id.rv_feed21) as RecyclerView
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = mAdapter
    }
}
