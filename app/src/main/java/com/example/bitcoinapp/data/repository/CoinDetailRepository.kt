package com.example.bitcoinapp.data.repository

import com.example.bitcoinapp.commonUtils.Results
import com.example.bitcoinapp.commonUtils.StringConstants.Companion.errorMessage
import com.example.bitcoinapp.data.network.CoinApiService
import com.example.bitcoinapp.data.network.modal.coinDeatails.CoinDetailModal
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Repository class responsible for fetching detailed information about cryptocurrencies from the API.
 *
 * @property coinApiService The service used to fetch cryptocurrency data.
 */
class CoinDetailRepository @Inject constructor(private val coinApiService: CoinApiService) {

    /**
     * Fetches detailed information about a specific cryptocurrency.
     *
     * @param coinId The unique identifier of the cryptocurrency.
     * @return A [Flow] emitting [Results] containing either the [CoinDetailModal]
     * on success or an error message on failure.
     */
    fun fetchCoinDetails(coinId: String): Flow<Results<CoinDetailModal>> = flow {
        try {
            val response = coinApiService.fetchCoinDetails(coinId)
            if(response.isSuccessful) {
                response.body()?.let { body ->
                    emit(Results.Success(body))
                } ?: emit(Results.Failure(errorMessage))
            } else {
                emit(Results.Failure(errorMessage))
            }
        } catch (e: Exception) {
            emit(Results.Failure(errorMessage))
        }
    }
}