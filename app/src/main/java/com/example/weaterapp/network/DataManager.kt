package com.example.weaterapp.network

import android.util.Log
import com.example.weaterapp.data.model.Reply
import com.example.weaterapp.network.client.RetrofitClient
import com.example.weaterapp.network.service.ApiService
import com.example.weaterapp.network.client.callback.OnResponseCallback
import com.example.weaterapp.utils.Constants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object DataManager {

    private lateinit var onResponseCallback: OnResponseCallback

    fun getResponse(onResponseCallback: OnResponseCallback) {
        this.onResponseCallback = onResponseCallback
        val api = RetrofitClient.getClient(Constants.HOST)
        val response: ApiService = api.create(ApiService::class.java)
        val call: Call<Reply> = response.getReply(46.482525,30.723309, Constants.EXCLUDE, Constants.KEY )
        call.enqueue(object : Callback<Reply> {
            override fun onResponse(call: Call<Reply>, response: Response<Reply>) {
                onResponseCallback.onResponse(response.body()!!)
            }
            override fun onFailure(call: Call<Reply>, t: Throwable) {
                Log.e(Constants.TAG, t.toString())
            }

        }
        )
    }

}