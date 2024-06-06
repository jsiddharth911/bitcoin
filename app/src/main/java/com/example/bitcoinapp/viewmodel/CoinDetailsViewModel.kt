package com.example.bitcoinapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitcoinapp.commonUtils.Results
import com.example.bitcoinapp.commonUtils.Results.Success
import com.example.bitcoinapp.commonUtils.StringConstants.Companion.errorMessage
import com.example.bitcoinapp.data.network.modal.coinDeatails.CoinDetailModal
import com.example.bitcoinapp.data.repository.CoinDetailRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel responsible for managing the state related to detailed cryptocurrency information.
 *
 * @property coinDetailRepository The repository responsible for fetching coin details.
 */
@HiltViewModel
class CoinDetailsViewModel @Inject
constructor(private val coinDetailRepository: CoinDetailRepository) : ViewModel() {

    private val _state: MutableStateFlow<Results<CoinDetailModal>> = MutableStateFlow(
        Success(CoinDetailModal())
    )

    /**
     * A read-only [StateFlow] representing the current state of the coin details data.
     */
    val coinsData: StateFlow<Results<CoinDetailModal>> = _state

    private val _isRefreshing = MutableStateFlow(true)

    /**
     * A read-only [StateFlow] representing the current refreshing state.
     */
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    /**
     * Fetches detailed information about a cryptocurrency.
     *
     * @param coinId The unique identifier of the cryptocurrency.
     */
    fun fetchCoinDetails(coinId: String) {
        viewModelScope.launch {
            try {
                coinDetailRepository.fetchCoinDetails(coinId)
                    .flowOn(Dispatchers.IO)
                    .catch {
                        _state.value = Results.Failure(errorMessage)
                        _isRefreshing.value = false
                    }
                    .collect { results ->
                        when (results) {
                            is Results.Failure -> {
                                _state.value = Results.Failure(errorMessage)
                                _isRefreshing.value = false
                            }
                            is Success -> {
                                _state.value = results
                                _isRefreshing.value = false
                            }
                        }
                    }
            } catch (e: Exception) {
                _state.value = Results.Failure(errorMessage)
                _isRefreshing.value = false
            }
        }
    }
}