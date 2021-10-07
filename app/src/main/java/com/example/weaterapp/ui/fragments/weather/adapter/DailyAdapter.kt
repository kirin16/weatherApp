package com.example.weaterapp.ui.fragments.weather.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weaterapp.R
import com.example.weaterapp.data.model.Daily
import com.example.weaterapp.ui.fragments.weather.adapter.callback.OnDailyItemChangeCallback
import com.example.weaterapp.utils.Constants
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class DailyAdapter (private val articleList: List<Daily>,
                    private val context: Context?,
                    private val onDailyItemChangeListener: OnDailyItemChangeCallback,
                    private var previousIndex: Int = -1):
    RecyclerView.Adapter<DailyAdapter.WeeklyItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeeklyItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.daily_item, parent, false)
        return WeeklyItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: WeeklyItemViewHolder, position: Int) {
        holder.bind(articleList[position])

        if (previousIndex == position){
            holder.setItemColor(false)
            Log.e(Constants.TAG, "1")
        }
        else {
            holder.setItemColor(true)
            Log.e(Constants.TAG, "2")
        }

        holder.itemView.setOnClickListener{
            previousIndex = position
            onDailyItemChangeListener.onDailyItemClick(position)
            notifyDataSetChanged()
        }
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class WeeklyItemViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        private var mDate: TextView = itemView.findViewById(R.id.tvWeeklyDate)
        private var mTemperature: TextView = itemView.findViewById(R.id.tvWeeklyTemperature)
        private var mImg: ImageView = itemView.findViewById(R.id.ivWeeklyImg)

        fun bind(article: Daily) {
            mDate.text = getDayOfWeekend(article.dt)
            mTemperature.text = getMinMaxTemp(article.temp.max, article.temp.min)

        }

        fun setItemColor(isSelected: Boolean) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                if(!isSelected) {
                    if (context != null) {
                        mDate.setTextColor(context.getColor(R.color.blue_white))
                        mTemperature.setTextColor(context.getColor(R.color.blue_white))
                        mImg.setImageResource(R.drawable.img_9)
                    }
                }
                else {
                    if (context != null) {
                        mDate.setTextColor(context.getColor(R.color.black))
                        mTemperature.setTextColor(context.getColor(R.color.black))
                        mImg.setImageResource(R.drawable.img_8)
                    }
                }
            }
        }

        private fun getDayOfWeekend(s: Long): String? {
            return try {
                val sdf = SimpleDateFormat("E")
                val netDate = Date(s * 1000)
                sdf.format(netDate)
            } catch (e: Exception) {
                e.toString()
            }
        }

        private fun getMinMaxTemp(x: Double, y: Double): String? {
            return try {
                val max = x - 273.15
                val min = y - 273.15
                context?.getString(R.string.celsius_daily, max.roundToInt().toString(), min.roundToInt().toString())
            } catch (e: Exception) {
                e.toString()
            }
        }

    }


}