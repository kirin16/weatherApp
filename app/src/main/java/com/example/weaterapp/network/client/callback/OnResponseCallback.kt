package com.example.weaterapp.network.client.callback

import com.example.weaterapp.data.model.Reply

interface OnResponseCallback {
    fun onResponse (reply: Reply)
}