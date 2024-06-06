package com.example.bitcoinapp.data.network.modal.coinDeatails

import com.google.gson.annotations.SerializedName

data class Links(
    val explorer: List<String> = emptyList(),
    val facebook: List<String> = emptyList(),
    val reddit: List<String> = emptyList(),
    @SerializedName("source_code")
    val sourceCode: List<String> = emptyList(),
    val website: List<String> = emptyList(),
    val youtube: List<String> = emptyList()
)