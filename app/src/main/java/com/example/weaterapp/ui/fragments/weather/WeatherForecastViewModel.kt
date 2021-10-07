package com.example.weaterapp.ui.fragments.weather

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.weaterapp.data.model.Reply
import com.example.weaterapp.network.DataManager
import com.example.weaterapp.network.client.callback.OnResponseCallback

class WeatherForecastViewModel: ViewModel(), OnResponseCallback {

    private var replyLiveData: MutableLiveData<Reply>? = null

    fun getData() : MutableLiveData<Reply>? {

        if (replyLiveData == null) {
            replyLiveData = MutableLiveData<Reply>()
            DataManager.getResponse(this)
        }

        return replyLiveData
    }

    override fun onResponse(reply: Reply) {
        replyLiveData?.value = reply
    }


}