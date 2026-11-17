package com.tradingviewtask.app.features.community

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.tradingviewtask.shared.ui.components.AppScaffold
import com.tradingviewtask.shared.ui.components.AppTopBar

data class Post(val author: String, val handle: String, val content: String, val likes: Int, val comments: Int)

class CommunityScreen : Screen {
    @Composable
    override fun Content() {
        val posts = listOf(
            Post("Alex Trader", "@alextrader", "Bullish on NVDA earnings next week! AI chip demand remains extremely high.", 342, 45),
            Post("Sarah Market", "@sarah_fx", "EUR/USD is testing key resistance at 1.0850. Watch out for NFP data release.", 128, 19),
            Post("Crypto Whale", "@whale_crypto", "Bitcoin consolidation near all-time highs. Breakout imminent?", 890, 134)
        )

        AppScaffold(
            modifier = Modifier.background(color = Color.White),
            topBar = {
                AppTopBar(title = "Community & Ideas", isLargeTitle = true)
            }
        ) { paddingValues ->

        }
    }
}
