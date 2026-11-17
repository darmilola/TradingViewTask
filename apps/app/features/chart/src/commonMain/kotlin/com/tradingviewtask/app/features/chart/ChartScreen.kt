package com.tradingviewtask.app.features.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.tradingviewtask.shared.ui.components.AppScaffold
import com.tradingviewtask.shared.ui.components.AppTopBar

class ChartScreen : Screen {
    @Composable
    override fun Content() {
        var selectedInterval by remember { mutableStateOf("1D") }
        val intervals = listOf("1H", "1D", "1W", "1M", "1Y", "All")

        AppScaffold(
            modifier = Modifier.background(color = Color.White),
            topBar = {
                AppTopBar(title = "Chart Engine", isLargeTitle = true)
            }
        ) { paddingValues ->

        }
    }

}
