package com.jeric.bitteldigitalsignage.ui.controller

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.toColorInt
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

class FeedAdapter(val media: List<EventFeedModel>, private val layoutId: Int): ListAdapter<EventFeedModel, FeedAdapter.FeedViewHolder>(FeedComparator()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FeedViewHolder {
        val layoutID = LayoutManager.getLayoutManager().getFeedItemLayout(layoutId)
        val mView: View = LayoutInflater.from(parent.context).inflate(layoutID, parent, false)
        return FeedViewHolder(mView)
    }
    override fun onBindViewHolder(holder: FeedViewHolder, position: Int) {
        val current = getItem(position)
        when(layoutId){
            46 -> {
                if(position == 0) {
                    holder.itemView.findViewById<View>(R.id.view1).setBackgroundColor("#01a453".toColorInt())
                } else {
                    holder.itemView.findViewById<View>(R.id.view1).setBackgroundColor("#eb1920".toColorInt())
                }
            }
            43 -> {
                if(position == 0) {
                    holder.itemView.findViewById<View>(R.id.view1).setBackgroundColor("#01a453".toColorInt())
                } else {
                    holder.itemView.findViewById<View>(R.id.view1).setBackgroundColor("#eb1920".toColorInt())
                }
            }

        }
        holder.bind(current)
    }
    inner class FeedViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        @SuppressLint("SimpleDateFormat", "SetTextI18n")
        fun bind(data: EventFeedModel) {
            CoroutineScope(Dispatchers.Main).launch {
                launch {
                    val logo = view.findViewById<ImageView>(R.id.iv_logo)
                    val tvCompany = view.findViewById<TextView>(R.id.tv_company)
                    val tvLocation = view.findViewById<TextView>(R.id.tv_location)
                    val tvFloor = view.findViewById<TextView>(R.id.tv_floor)
                    val tvSchedule = view.findViewById<TextView>(R.id.tv_schedule)
                    val tvDesc = view.findViewById<TextView>(R.id.tv_description)
                    val imageUri = "${STB.HOST}:${STB.PORT}/" + data.imgUri
                    logo?.load(imageUri) { memoryCachePolicy(CachePolicy.DISABLED) }
                    tvCompany?.text = data.owner
                    tvLocation?.text = data.location
                    tvDesc?.text = data.description
                    tvFloor?.text = ""
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
                            37 -> {
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
                            46 -> {
                                tvSchedule?.text = "$start - $end"
                            }
                            39 ->  {
                                tvSchedule?.text = "$start - $end"
                            }
                            40 ->  {
                                tvSchedule?.text = "$start - $end"
                            }
                            42 ->  {
                                tvSchedule?.text = "$start - $end"
                            }
                            43 ->  {
                                tvSchedule?.text = "$start - $end"
                            }
                            45 ->  {
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