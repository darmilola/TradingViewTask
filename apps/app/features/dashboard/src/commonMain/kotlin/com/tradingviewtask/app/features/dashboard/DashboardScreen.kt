package com.tradingviewtask.app.features.dashboard

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.core.screen.Screen

class DashboardScreen : Screen {
    @Composable
    override fun Content() {
        DashboardScreens.Main().Content()
    }
}
