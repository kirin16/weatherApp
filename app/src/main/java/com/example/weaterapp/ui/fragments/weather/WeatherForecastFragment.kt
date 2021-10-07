package com.example.weaterapp.ui.fragments.weather

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.weaterapp.R
import com.example.weaterapp.data.model.Reply
import com.example.weaterapp.ui.fragments.weather.adapter.callback.OnDailyItemChangeCallback
import com.example.weaterapp.ui.fragments.weather.adapter.DailyAdapter
import com.example.weaterapp.ui.fragments.weather.adapter.HourlyAdapter
import java.text.SimpleDateFormat
import java.util.*

class WeatherForecastFragment : Fragment(), OnDailyItemChangeCallback {

    private lateinit var tvHumidity: TextView
    private lateinit var tvTemperature: TextView
    private lateinit var tvWindSpeed: TextView
    private lateinit var tvDate: TextView

    private lateinit var ivLocation: ImageView

    private lateinit var mDailyRecyclerView: RecyclerView
    private lateinit var mWeeklyRecyclerView: RecyclerView
    private lateinit var mHourlyAdapter: HourlyAdapter
    private lateinit var mDailyAdapter: DailyAdapter

    private lateinit var mReply: Reply

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_weather_forecast, container, false)

        val viewModel = WeatherForecastViewModel()
        viewModel.getData()?.observe(requireActivity(), {
            mReply = it
            fillData()
        })

        initView(view)
        initUI()

        return view
    }

    private fun initView (view: View) {
        mDailyRecyclerView = view.findViewById(R.id.rvDailyForecast)
        mWeeklyRecyclerView = view.findViewById(R.id.rvWeeklyForecast)

        tvHumidity = view.findViewById(R.id.tvHumidity)
        tvWindSpeed = view.findViewById(R.id.tvWindSpeed)
        tvTemperature = view.findViewById(R.id.tvTemperature)
        tvDate = view.findViewById(R.id.tvTodayDate)
        ivLocation = view.findViewById(R.id.ivLocation)
    }

    private fun initUI() {

        ivLocation.setOnClickListener {
            findNavController().navigate(R.id.mapsFragment)
        }

        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.orientation = LinearLayoutManager.HORIZONTAL
        mDailyRecyclerView.layoutManager = mLayoutManager
        mWeeklyRecyclerView.layoutManager = LinearLayoutManager(context)
    }

    private fun fillData() {
        mHourlyAdapter = HourlyAdapter(mReply.hourly, context)
        mHourlyAdapter.notifyDataSetChanged()
        mDailyAdapter = DailyAdapter(mReply.daily, context, this)
        mDailyAdapter.notifyDataSetChanged()

        mDailyRecyclerView.adapter = mHourlyAdapter
        mWeeklyRecyclerView.adapter = mDailyAdapter

        tvTemperature.text = getString(R.string.celsius_hourly, (mReply.current.temp - 273.15).toInt().toString())
        tvHumidity.text = mReply.current.humidity.toString()
        tvWindSpeed.text = getString(R.string.wind_speed, mReply.current.wind_speed.toString())
        tvDate.text = getDate(mReply.current.dt)
    }

    fun Reply.isTrue() : Boolean {
        return this.lat == 100.0
    }

    private fun getDate(s: Long): String? {
        return try {
            val sdf = SimpleDateFormat("E, dd MMMM")
            val netDate = Date(s * 1000)
            sdf.format(netDate)
        } catch (e: Exception) {
            e.toString()
        }
    }

    override fun onDailyItemClick(position: Int) {
        tvTemperature.text = getString(R.string.celsius_hourly, (mReply.daily[position].temp.max - 273.15).toInt().toString())
        tvHumidity.text = mReply.daily[position].humidity.toString()
        tvWindSpeed.text = getString(R.string.wind_speed, mReply.daily[position].wind_speed.toString())
        tvDate.text = getDate(mReply.daily[position].dt)
    }

}