package com.jeric.bitteldigitalsignage.ui.controller

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.request.CachePolicy
import com.jeric.bitteldigitalsignage.R
import com.jeric.bitteldigitalsignage.datastore.model.STB
import com.jeric.bitteldigitalsignage.network.domain.model.EventFeedModel
import com.jeric.bitteldigitalsignage.ui.components.LayoutManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class FeedAdapterStatus(val media: List<EventFeedModel>, private val layoutId: Int): ListAdapter<EventFeedModel, FeedAdapterStatus.FeedViewHolder>(FeedComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val layoutID = LayoutManager.getLayoutManager().getFeedItemLayout(layoutId)
        val mView: View = LayoutInflater.from(parent.context).inflate(layoutID, parent, false)
        return FeedViewHolder(mView)
    }
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)

    }

    inner class FeedViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SimpleDateFormat")
        fun bind(data: EventFeedModel) {
            CoroutineScope(Dispatchers.Main).launch {
                launch {
                    val logo = view.findViewById<ImageView>(R.id.iv_logo)
                    val tvCompany = view.findViewById<TextView>(R.id.tv_company)
                    val tvLocation = view.findViewById<TextView>(R.id.tv_location)
                    val tvGroupEvent = view.findViewById<TextView>(R.id.tv_group_event)
                    val tvFloor = view.findViewById<TextView>(R.id.tv_floor)
                    val tvStart = view.findViewById<TextView>(R.id.rv_start)
                    val tvEnd = view.findViewById<TextView>(R.id.tv_endtime)
                    val tvStat = view.findViewById<TextView>(R.id.tv_stat)
                    val tvStatus = view.findViewById<ImageView>(R.id.status)
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
                        when (layoutId) {
                            21 -> {
                                tvStart.text = start
                                tvEnd.text = end
                                tvEnd.text = end
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
    class FeedComparator : DiffUtil.ItemCallback<EventFeedModel>() {
        override fun areItemsTheSame(oldItem: EventFeedModel, newItem: EventFeedModel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: EventFeedModel, newItem: EventFeedModel) = oldItem == newItem
    }

}