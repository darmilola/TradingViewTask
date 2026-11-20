package com.tradingviewtask.app.features.dashboard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.tradingviewtask.app.features.chart.ChartScreen
import com.tradingviewtask.app.features.community.CommunityScreen
import com.tradingviewtask.app.features.dashboard.profile.ProfileScreen
import com.tradingviewtask.app.features.watchlists.WatchlistsScreen
import com.tradingviewtask.shared.common.Instrument
import com.tradingviewtask.shared.ui.components.AppScaffold
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import tradingviewtask.apps.app.features.dashboard.generated.resources.Res
import tradingviewtask.apps.app.features.dashboard.generated.resources.ic_chart
import tradingviewtask.apps.app.features.dashboard.generated.resources.ic_community
import tradingviewtask.apps.app.features.dashboard.generated.resources.ic_profile
import tradingviewtask.apps.app.features.dashboard.generated.resources.ic_watchlist

internal enum class DashboardMainTab(
    val title: String,
    val icon: DrawableResource
) {
    Watchlist("Watchlist", Res.drawable.ic_watchlist),
    Chart("Chart", Res.drawable.ic_chart),
    Community("Community", Res.drawable.ic_community),
    Profile("Profile", Res.drawable.ic_profile)
}

object DashboardScreens {
    data class Main(val startTab: String? = "Watchlist") : Screen {
        @Composable
        override fun Content() {
            var selectedTab by remember { mutableStateOf(DashboardMainTab.Watchlist) }
            var currentInstrument by remember { mutableStateOf(Instrument.NVDA) }

            val selectedColor = MaterialTheme.colorScheme.onSurface
            val unselectedColor = MaterialTheme.colorScheme.onSurfaceVariant

            AppScaffold(
                modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
                containerColor = MaterialTheme.colorScheme.background,
                bottomBar = {
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shadowElevation = 8.dp,
                        tonalElevation = 8.dp,
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        NavigationBar(
                            modifier = Modifier.height(90.dp),
                            containerColor = Color.Transparent,
                            tonalElevation = 0.dp
                        ) {
                            val tabs = DashboardMainTab.entries

                            tabs.forEach { tab ->
                                val isSelected = selectedTab == tab

                                NavigationBarItem(
                                    selected = isSelected,
                                    onClick = { selectedTab = tab },
                                    icon = {
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Icon(
                                                painter = painterResource(tab.icon),
                                                contentDescription = tab.title,
                                                tint = if (isSelected) selectedColor else unselectedColor,
                                                modifier = Modifier.height(24.dp).width(32.dp)
                                            )
                                        }
                                    },
                                    label = { Text(tab.title, color = if (isSelected) selectedColor else unselectedColor) },
                                    colors = NavigationBarItemDefaults.colors(
                                        indicatorColor = Color.Transparent
                                    )
                                )
                            }
                        }
                    }
                }
            ) { paddingValues ->
                Box(modifier = Modifier.fillMaxSize()) {
                    when (selectedTab) {
                        DashboardMainTab.Watchlist -> WatchlistsScreen(
                            selectedInstrument = currentInstrument,
                            onInstrumentSelect = { instrument ->
                                currentInstrument = instrument
                                selectedTab = DashboardMainTab.Chart
                            }
                        ).Content()
                        DashboardMainTab.Chart -> ChartScreen(
                            selectedInstrument = currentInstrument,
                            onInstrumentSelect = { instrument ->
                                currentInstrument = instrument
                            }
                        ).Content()
                        DashboardMainTab.Community -> CommunityScreen().Content()
                        DashboardMainTab.Profile -> ProfileScreen().Content()
                    }
                }
            }
        }
    }
}
