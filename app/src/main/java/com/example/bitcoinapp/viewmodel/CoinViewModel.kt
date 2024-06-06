package com.example.bitcoinapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bitcoinapp.commonUtils.Results
import com.example.bitcoinapp.commonUtils.StringConstants.Companion.errorMessage
import com.example.bitcoinapp.data.network.modal.coins.CoinModal
import com.example.bitcoinapp.data.repository.CoinRepository
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
 * ViewModel responsible for managing the state related to cryptocurrency data.
 *
 * @property coinRepository The repository responsible for fetching cryptocurrency data.
 */
@HiltViewModel
class CoinViewModel @Inject constructor(private val coinRepository: CoinRepository): ViewModel() {

    private val _state: MutableStateFlow<Results<CoinModal>> = MutableStateFlow(Results.Success(
        CoinModal()
    ))

    /**
     * A read-only [StateFlow] representing the current state of the cryptocurrency data.
     */
    val coinsData: StateFlow<Results<CoinModal>> = _state

    private val _isRefreshing = MutableStateFlow(true)

    /**
     * A read-only [StateFlow] representing the current refreshing state.
     */
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    init {
        fetchCoinData()
    }

    /**
     * Fetches a list of all available cryptocurrencies.
     */
    private fun fetchCoinData() {
        viewModelScope.launch {
            try {
                coinRepository.fetchCoinsData()
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
                            is Results.Success -> {
                                // Ensure that result.data is not null before updating the state
                                results.data?.let {
                                    _state.value = Results.Success(it)
                                }
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

    /**
     * Refreshes the list of cryptocurrencies.
     */
    fun refreshCoins() {
        viewModelScope.launch {
            _isRefreshing.value = true
            fetchCoinData()
        }
    }
}
