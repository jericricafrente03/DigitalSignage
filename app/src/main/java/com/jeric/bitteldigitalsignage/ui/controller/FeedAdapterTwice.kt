package com.jeric.bitteldigitalsignage.ui.controller

import android.annotation.SuppressLint
import android.graphics.Color
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

class FeedAdapterTwice(val media: List<EventFeedModel>, private val layoutId: Int): ListAdapter<EventFeedModel, FeedAdapterTwice.FeedViewHolder>(FeedComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val layoutID = LayoutManager.getLayoutManager().getFeedItemLayout(26)
        val mView: View = LayoutInflater.from(parent.context).inflate(layoutID, parent, false)
        return FeedViewHolder(mView)
    }
    @SuppressLint("UseKtx")
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val current = getItem(position)
        when(layoutId){
            50 -> {
                if(position == 0) {
                    holder.itemView.findViewById<View>(R.id.view1).setBackgroundColor(Color.parseColor("#01a453"))
                } else {
                    holder.itemView.findViewById<View>(R.id.view1).setBackgroundColor(Color.parseColor("#eb1920"))
                }
            }
        }
        holder.bind(current)
    }
    override fun getItemCount(): Int {
        return if (media.isNotEmpty()) 1 else 0
    }
    inner class FeedViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bind(data: EventFeedModel) {
            CoroutineScope(Dispatchers.Main).launch {
                launch {
                    val logo = view.findViewById<ImageView>(R.id.iv_logo)
                    val tvCompany = view.findViewById<TextView>(R.id.tv_company)
                    val tvLocation = view.findViewById<TextView>(R.id.tv_location)
                    val tvGroupEvent = view.findViewById<TextView>(R.id.tv_group_event)
                    val tvFloor = view.findViewById<TextView>(R.id.tv_floor)
                    val tvSchedule = view.findViewById<TextView>(R.id.tv_schedule)
                    val tvDesc = view.findViewById<TextView>(R.id.tv_description)

                    val imageUri = "${STB.HOST}:${STB.PORT}/" + data.imgUri
                    logo?.load(imageUri) {
                        memoryCachePolicy(CachePolicy.DISABLED)
                    }
                    tvGroupEvent?.text = data.owner
                    tvCompany?.text = data.owner
                    tvLocation?.text = data.location
                    tvFloor?.text = ""
                    tvDesc?.text = data.description
                    if (data.start?.isNotEmpty() == true && data.end?.isNotEmpty() == true) {
                        val format1 = SimpleDateFormat("yyyy-MM-dd kk:mm:ss")
                        val dateFormat3: DateFormat = SimpleDateFormat("hh:mm a")
                        val scheduleStartDate = format1.parse(data.start)
                        val scheduleEndDate = format1.parse(data.end)
                        val start = dateFormat3.format(scheduleStartDate!!)
                        val end = dateFormat3.format(scheduleEndDate!!)
                        val today = Calendar.getInstance()
                        val todayDay = today.get(Calendar.DAY_OF_YEAR)
                        val schedStart = Calendar.getInstance()
                        schedStart.time = scheduleStartDate
                        val schedEnd = Calendar.getInstance()
                        schedEnd.time = scheduleEndDate
                        when (layoutId) {
                            14 -> {
                                var schedule: String =
                                    if (schedStart.get(Calendar.DAY_OF_YEAR) != todayDay)
                                        SimpleDateFormat("hh:mm a").format(scheduleStartDate)
                                    else
                                        start.toString()
                                schedule = if (schedEnd.get(Calendar.DAY_OF_YEAR) != todayDay)
                                    "  $schedule -\n" + SimpleDateFormat("hh:mm a").format(
                                        scheduleEndDate
                                    )
                                else
                                    "  $schedule -\n$end"
                                tvSchedule?.text = schedule
                            }
                            50 -> {
                                tvSchedule?.text = "$start - $end"
                            }
                            22 ->  {
                                tvSchedule?.text = "$start - $end"
                            }
                        }
                    }
                }
            }
        }
    }
    class FeedComparator : DiffUtil.ItemCallback<EventFeedModel>() {
        override fun areItemsTheSame(oldItem: EventFeedModel, newItem: EventFeedModel) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: EventFeedModel, newItem: EventFeedModel) = oldItem == newItem
    }

}