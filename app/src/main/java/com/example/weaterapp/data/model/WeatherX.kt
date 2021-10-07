package com.example.weaterapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class WeatherX(
    val description: String,
    val icon: String,
    val id: Int,
    val main: String
) : Parcelable