package com.example.bitcoinapp.data.network.modal.coinDeatails

import com.google.gson.annotations.SerializedName

data class Tag(
    @SerializedName("coin_counter")
    val coinCounter: Int = 0,
    @SerializedName("ico_counter")
    val icoCounter: Int = 0,
    val id: String = "",
    val name: String = ""
)