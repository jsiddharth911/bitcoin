package com.example.bitcoinapp.data.network.modal.coinDeatails

import com.google.gson.annotations.SerializedName

data class CoinDetailModal(

    val description: String = "",
    @SerializedName("developmentStatus")
    val developmentStatus: String = "",
    @SerializedName("first_data_at")
    val firstDataAt: String = "",
    @SerializedName("hardware_wallet")
    val hardwareWallet: Boolean = false,
    @SerializedName("hash_algorithm")
    val hashAlgorithm: String = "",
    val id: String = "",
    @SerializedName("is_active")
    val isActive: Boolean = false,
    @SerializedName("is_new")
    val isNew: Boolean = false,
    @SerializedName("last_data_at")
    val lastDataAt: String = "",
    val links: Links = Links(),
    @SerializedName("links_extended")
    val linksExtended: List<LinksExtended> = emptyList(),
    val logo: String = "",
    val message: String = "",
    val name: String = "",
    @SerializedName("open_source")
    val openSource: Boolean = false,
    @SerializedName("org_structure")
    val orgStructure: String = "",
    @SerializedName("proof_type")
    val proofType: String = "",
    val rank: Int = 0,
    @SerializedName("started_at")
    val startedAt: String = "",
    val symbol: String = "",
    val tags: List<Tag> = emptyList(),
    val team: List<Team> = emptyList(),
    val type: String = "",
    @SerializedName("whitepaper")
    val whitePaper: Whitepaper = Whitepaper()
)