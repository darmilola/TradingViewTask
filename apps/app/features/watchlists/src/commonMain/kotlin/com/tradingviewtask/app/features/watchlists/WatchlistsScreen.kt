package com.tradingviewtask.app.features.watchlists

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.tradingviewtask.shared.ui.components.AppScaffold
import com.tradingviewtask.shared.ui.components.AppTopBar

data class WatchlistItem(val symbol: String, val name: String, val price: String, val change: String, val isPositive: Boolean)

class WatchlistsScreen : Screen {
    @Composable
    override fun Content() {
        val watchlistItems = listOf(
            WatchlistItem("AAPL", "Apple Inc.", "$228.50", "+1.25%", true),
            WatchlistItem("TSLA", "Tesla, Inc.", "$248.20", "-0.85%", false),
            WatchlistItem("BTCUSD", "Bitcoin USD", "$67,450.00", "+3.40%", true),
            WatchlistItem("EURUSD", "Euro / US Dollar", "$1.0820", "+0.15%", true),
            WatchlistItem("NVDA", "NVIDIA Corporation", "$135.80", "+4.12%", true)
        )

        AppScaffold(
            modifier = Modifier.background(color = Color.White),
            topBar = {
                AppTopBar(title = "Watchlists", isLargeTitle = true)
            }
        ) { paddingValues ->

        }
    }
}
