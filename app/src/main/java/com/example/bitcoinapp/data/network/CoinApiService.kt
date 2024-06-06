package com.example.bitcoinapp.data.network

import com.example.bitcoinapp.data.network.modal.coinDeatails.CoinDetailModal
import com.example.bitcoinapp.data.network.modal.coins.CoinModal
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * Interface defining the API endpoints for fetching cryptocurrency data.
 */
interface CoinApiService {

    /**
     * Fetches a list of all available cryptocurrencies.
     *
     * @return A [Response] object containing a list of [CoinModal] objects.
     */
    @GET("coins")
    suspend fun fetchBitcoins(): Response<CoinModal>

    /**
     * Fetches detailed information about a specific cryptocurrency.
     *
     * @param coinId The unique identifier of the cryptocurrency.
     * @return A [Response] object containing a [CoinDetailModal] object.
     */
    @GET("coins/{coinId}")
    suspend fun fetchCoinDetails(@Path("coinId") coinId: String): Response<CoinDetailModal>
}