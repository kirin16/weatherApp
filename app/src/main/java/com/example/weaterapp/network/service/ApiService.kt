package com.example.weaterapp.network.service

import com.example.weaterapp.data.model.Reply
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("data/2.5/onecall")
    fun getReply(@Query("lat") lat: Double,
                 @Query("lon") lon: Double,
                 @Query("exclude") exclude: String,
                 @Query("appid") appid: String)
    : Call<Reply>
}