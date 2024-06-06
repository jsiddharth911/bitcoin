package com.example.bitcoinapp.data.repository

import com.example.bitcoinapp.commonUtils.Results
import com.example.bitcoinapp.commonUtils.StringConstants.Companion.errorMessage
import com.example.bitcoinapp.data.network.CoinApiService
import com.example.bitcoinapp.data.network.modal.coins.CoinModal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Repository class responsible for fetching a list of cryptocurrencies from the API.
 *
 * @property coinService The service used to fetch cryptocurrency data.
 */
class CoinRepository @Inject constructor(private val coinService: CoinApiService) {

    /**
     * Fetches a list of all available cryptocurrencies.
     *
     * @return A [Flow] emitting [Results] containing either the [CoinModal] list on success or
     * an error message on failure.
     */
    fun fetchCoinsData(): Flow<Results<CoinModal?>> = flow {
        try {
            val response = coinService.fetchBitcoins()
            if (response.isSuccessful && !response.body().isNullOrEmpty()) {
                emit(Results.Success(response.body()))
            } else {
                emit(Results.Failure(errorMessage))
            }
        } catch (e: Exception) {
            emit(Results.Failure(errorMessage))
        }
    }
}