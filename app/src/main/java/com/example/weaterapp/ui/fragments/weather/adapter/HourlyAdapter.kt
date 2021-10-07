package com.example.weaterapp.ui.fragments.weather.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.weaterapp.R
import com.example.weaterapp.data.model.Hourly
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class HourlyAdapter (private val articleList: List<Hourly>, private val context: Context?):
    RecyclerView.Adapter<HourlyAdapter.DailyViewHolder>() {

    inner class DailyViewHolder (itemView: View): RecyclerView.ViewHolder(itemView) {

        private var mDate: TextView? = null
        private var mTemperature: TextView? = null

        init {

            mDate = itemView.findViewById(R.id.tvDailyDate)
            mTemperature = itemView.findViewById(R.id.tvDailyTemperature)

        }

        fun bind(hourly: Hourly) {
            mDate?.text = getTime(hourly.dt)
            mTemperature?.text = getTemp(hourly.temp)

        }

        private fun getTime(s: Long): String? {
            return try {
                val sdf = SimpleDateFormat("HH")
                val netDate = Date(s * 1000)
                sdf.format(netDate)
            } catch (e: Exception) {
                e.toString()
            }
        }

        private fun getTemp(x: Double): String? {
            return try {
                val max = x - 273.15
                context?.getString(R.string.celsius_hourly, max.roundToInt().toString())
            } catch (e: Exception) {
                e.toString()
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.hourly_item, parent, false)
        return DailyViewHolder(view)
    }

    override fun onBindViewHolder(holder: DailyViewHolder, position: Int) {
        holder.bind(articleList[position])
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

}