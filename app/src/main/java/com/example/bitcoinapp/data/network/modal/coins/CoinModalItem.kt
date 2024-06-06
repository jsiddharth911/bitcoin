package com.example.bitcoinapp.data.network.modal.coins

import com.google.gson.annotations.SerializedName

data class CoinModalItem(
    val id: String,
    @SerializedName("is_active")
    val isActive: Boolean,
    @SerializedName("is_new")
    val isNew: Boolean,
    val name: String,
    val rank: Int,
    val symbol: String,
    val type: String
)