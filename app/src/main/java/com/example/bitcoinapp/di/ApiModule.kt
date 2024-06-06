package com.example.bitcoinapp.di

import com.example.bitcoinapp.commonUtils.StringConstants.Companion.baseUrl
import com.example.bitcoinapp.data.network.CoinApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Module responsible for providing the Retrofit instance and the associated API service using Dagger Hilt.
 */
@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    /**
     * Provides a singleton instance of Retrofit configured with base URL and Gson converter.
     * @return A singleton instance of Retrofit.
     */
    @Provides
    @Singleton
    fun createRetrofitInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * Provides a singleton instance of the Coins api interface using the Retrofit instance.
     * @param retrofit The Retrofit instance.
     * @return A singleton instance of the CoinApiService interface.
     */
    @Provides
    @Singleton
    fun createCoinApiServiceInstance(retrofit: Retrofit): CoinApiService {
        return retrofit.create(CoinApiService::class.java)
    }
}