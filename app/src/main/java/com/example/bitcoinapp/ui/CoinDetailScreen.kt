package com.example.bitcoinapp.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.bitcoinapp.R
import com.example.bitcoinapp.commonUtils.Results
import com.example.bitcoinapp.data.network.modal.coinDeatails.CoinDetailModal
import com.example.bitcoinapp.data.network.modal.coinDeatails.Tag
import com.example.bitcoinapp.data.network.modal.coinDeatails.Team
import com.example.bitcoinapp.viewmodel.CoinDetailsViewModel

/**
 * Composable function responsible for displaying detailed information about a specific cryptocurrency.
 *
 * @param viewModel The view model to manage the state and fetch data.
 * @param coinId The ID of the cryptocurrency to fetch details for.
 */
@Composable
fun CoinDetailScreen(
    viewModel: CoinDetailsViewModel = hiltViewModel(),
    coinId: String
) {
    viewModel.fetchCoinDetails(coinId)
    val coinDetailData by viewModel.coinsData.collectAsState()
    val refreshState by viewModel.isRefreshing.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            when (val currentState = coinDetailData) {
                is Results.Success -> {
                    val coins = currentState.data
                    InfoSection(coins)
                    if(coins.tags.isNotEmpty())
                    Tags(coins.tags)

                    if(coins.team.isNotEmpty())
                    Team(coins.team)
                }

                is Results.Failure -> {
                    Text(
                        text = currentState.message,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }

        if (refreshState) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f)),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
    }
}


/**
 * Composable function responsible for displaying the information section of a coin.
 *
 * @param coinDetail The detailed information of the coin to display.
 */
@Composable
fun InfoSection(coinDetail: CoinDetailModal) {
    Column (verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier.padding(10.dp)){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            if(coinDetail.logo.isNotEmpty()) {
                val painter = rememberAsyncImagePainter(coinDetail.logo)
                Image(
                    painter = painter,
                    contentDescription = "Your Image",
                    modifier = Modifier.size(64.dp),
                    contentScale = ContentScale.Fit
                )
            }
            Spacer(modifier = Modifier.width(8.dp))

            Column(
                verticalArrangement = Arrangement.Center
            ) {
                if(coinDetail.name.isNotEmpty())
                Text(text = coinDetail.name, fontSize = 18.sp)

                Row {
                    if(coinDetail.symbol.isNotEmpty())
                    Text(text = coinDetail.symbol, fontSize = 14.sp, color = Color.Red)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(text = "Rank - ${coinDetail.rank}", fontSize = 14.sp)
                }
            }
        }
        
        Text(text = coinDetail.description, fontSize = 13.sp)
    }
    
}

/**
 * Composable function responsible for displaying tags associated with a coin.
 *
 * @param tags The list of tags to display.
 */
@Composable
fun Tags(tags: List<Tag>) {
    var isVisible by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isVisible = !isVisible }
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Tags",
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { isVisible = !isVisible }
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isVisible) R.drawable.baseline_keyboard_arrow_up
                        else R.drawable.baseline_keyboard_arrow_down
                    ),
                    contentDescription = "Toggle Tags Visibility"
                )
            }
        }

        if (isVisible) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                tags.forEach { tags ->
                    TagCard(tag = tags)
                }
            }
        }
    }
}

/**
 * Composable function responsible for displaying teams associated with a coin.
 *
 * @param team The list of teams to display.
 */
@Composable
fun Team(team: List<Team>) {
    var isVisible by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isVisible = !isVisible }
                .padding(horizontal = 16.dp)
        ) {
            Text(
                text = "Teams",
                fontSize = 20.sp,
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = { isVisible = !isVisible }
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isVisible) R.drawable.baseline_keyboard_arrow_up else R.drawable.baseline_keyboard_arrow_down
                    ),
                    contentDescription = "Toggle Teams Visibility"
                )
            }
        }

        if (isVisible) {
            Column(
                modifier = Modifier.padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                team.forEach { team ->
                    Team(team = team)
                }
            }
        }
    }
}

/**
 * Composable function responsible for displaying a single tag card.
 *
 * @param tag The tag information to display.
 */
@Composable
fun TagCard(tag: Tag) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(Color.Blue),
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = tag.name, fontSize = 16.sp,
                color = Color.White, modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Coin Count: ${tag.coinCounter}",
                fontSize = 14.sp,
                color = Color.White,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

/**
 * Composable function responsible for displaying information about a team associated with a coin.
 *
 * @param team The team information to display.
 */
@Composable
fun Team(team: Team) {
    Card(
        elevation = CardDefaults.cardElevation(4.dp),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(Color.White),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = team.name, fontSize = 18.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Position: ${team.position}", fontSize = 14.sp, color = Color.Black)
        }
    }
}
