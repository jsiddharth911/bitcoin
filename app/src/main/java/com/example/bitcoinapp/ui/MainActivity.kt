package com.example.bitcoinapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.bitcoinapp.ui.theme.BitCoinAppTheme
import dagger.hilt.android.AndroidEntryPoint

/**
 * Main entry point of the application.
 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BitCoinAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavigationForScreens()
                }
            }
        }
    }
}

/**
 * Composable function responsible for setting up the navigation within the application.
 */
@Composable
fun NavigationForScreens() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "coin_list_screen") {
        composable("coin_list_screen") {
            CoinData(navController = navController)
        }
        composable(
            "coin_detail_screen/{coinId}",
            arguments = listOf(navArgument("coinId") { type = NavType.StringType })
        ) { backStackEntry ->
            val coinId = backStackEntry.arguments?.getString("coinId") ?: ""
            CoinDetailScreen(coinId = coinId)
        }
    }
}