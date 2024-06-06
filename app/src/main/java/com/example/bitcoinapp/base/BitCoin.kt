package com.example.bitcoinapp.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Base class for maintaining global application state.
 * Hilt dependency injection library built on top of Dagger 2 for Android apps.
 */
@HiltAndroidApp
class BitCoin: Application()