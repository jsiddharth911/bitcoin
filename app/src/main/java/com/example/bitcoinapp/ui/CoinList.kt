package com.example.bitcoinapp.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.bitcoinapp.commonUtils.Results
import com.example.bitcoinapp.data.network.modal.coins.CoinModalItem
import com.example.bitcoinapp.viewmodel.CoinViewModel


/**
 * Composable function responsible for displaying a list of cryptocurrencies.
 *
 * @param viewModel The view model to manage the state and fetch data.
 * @param navController The navigation controller used for navigating to the coin detail screen.
 */
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CoinData(viewModel: CoinViewModel = hiltViewModel(), navController: NavController) {
    val viewState by viewModel.coinsData.collectAsState()
    val refreshState by viewModel.isRefreshing.collectAsState()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshState,
        onRefresh = viewModel::refreshCoins
    )

    Box(
        modifier = Modifier
            .pullRefresh(pullRefreshState)
            .fillMaxSize()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(
                text = "Coins",
                fontFamily = FontFamily.Default,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier.padding(top = 15.dp, start = 10.dp, end = 10.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            CoinItemHeadings()

            // Display content based on viewState
            when (val currentState = viewState) {
                is Results.Success -> {
                    val coins = currentState.data
                    if (coins.isNotEmpty()) {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize()
                        ) {
                            items(coins) { coin ->
                                CoinItem(coin,
                                    onItemClick = { coinId ->
                                        navController.navigate("coin_detail_screen/$coinId")
                                    })
                            }
                        }
                    } else {
                        Text(
                            text = "No coins available",
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                    }
                }
                is Results.Failure -> {
                    Text(
                        text = currentState.message,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(top = 20.dp)
                    )
                }
            }
        }

        PullRefreshIndicator(
            state = pullRefreshState,
            refreshing = refreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}


/**
 * Composable function responsible for displaying headings of the coin list items.
 */
@Composable
fun CoinItemHeadings() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .background(MaterialTheme.colorScheme.primary)
            .padding(16.dp)
    ) {
        Text(
            text = "Name",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = "Symbol",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = "Rank",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = "Active",
            modifier = Modifier.weight(1f),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

/**
 * Composable function responsible for displaying a single coin item.
 *
 * @param coin The coin data to display.
 * @param onItemClick The callback function to handle item click events.
 */
@Composable
fun CoinItem(coin: CoinModalItem, onItemClick: (String) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .clickable {
                onItemClick(coin.id)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = coin.name, modifier = Modifier.weight(1f))
            Text(text = coin.symbol, modifier = Modifier.weight(1f))
            Text(text = coin.rank.toString(), modifier = Modifier.weight(1f))
            Text(text = coin.isActive.toString(), modifier = Modifier.weight(1f))
        }
    }
}