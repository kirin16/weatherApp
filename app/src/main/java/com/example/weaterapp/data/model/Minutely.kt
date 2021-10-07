package com.example.weaterapp.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Minutely(
    val dt: Int,
    val precipitation: Int
) : Parcelable