package com.tradingviewtask.app.features.subscription

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.tradingviewtask.shared.ui.components.AppScaffold
import com.tradingviewtask.shared.ui.components.AppTopBar

class SubscriptionScreen : Screen {
    @Composable
    override fun Content() {
        AppScaffold(
            topBar = {
                AppTopBar(title = "Subscription Plans", isLargeTitle = true)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("Unlock Pro Trading Features", style = MaterialTheme.typography.headlineMedium)

                Card(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Pro Plan", style = MaterialTheme.typography.titleLarge)
                        Text("$14.95 / month", style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("• Advanced Charting & Indicators")
                        Text("• Real-time Data Feeds")
                        Text("• Unlimited Watchlists")
                        Text("• Priority Community Support")
                    }
                }

                Button(
                    onClick = { /* Handle Subscription */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Subscribe Now")
                }
            }
        }
    }
}
