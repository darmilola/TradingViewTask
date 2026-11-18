package com.tradingviewtask.app

import androidx.compose.runtime.Composable
import cafe.adriel.voyager.navigator.Navigator
import com.tradingviewtask.app.features.dashboard.DashboardScreen
import com.tradingviewtask.shared.ui.theme.AppTheme

public interface DeepLinkHandler {
    public fun handleDeepLink(url: String) {}
}

public interface DataHandler {
    public fun handleData(data: Map<String, String>) {}
}

@Composable
public fun AppContent(
    deepLinkHandler: DeepLinkHandler? = null,
    dataHandler: DataHandler? = null
) {
    App()
}

@Composable
public fun App() {
    AppTheme {
        Navigator(screen = DashboardScreen())
    }
}
