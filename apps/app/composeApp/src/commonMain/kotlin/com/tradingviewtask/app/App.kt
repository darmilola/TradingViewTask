package com.tradingviewtask.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.tradingviewtask.app.features.dashboard.DashboardScreen
import com.tradingviewtask.shared.ui.theme.AppTheme

@Composable
fun App() {
    AppTheme {
        Navigator(screen = DashboardScreen())
    }
}
