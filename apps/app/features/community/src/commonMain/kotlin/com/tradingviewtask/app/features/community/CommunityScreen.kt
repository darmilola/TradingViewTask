package com.tradingviewtask.app.features.community

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
import com.tradingviewtask.shared.ui.components.WebViewComposable
import com.tradingviewtask.shared.ui.components.WebViewLoadingState
import com.tradingviewtask.shared.ui.components.WebViewState

class CommunityScreen : Screen {
    @Composable
    override fun Content() {
        val currentUrl = "https://www.tradingview.com/ideas/"
        var webViewState by remember { mutableStateOf<WebViewState?>(null) }

        AppScaffold(
            modifier = Modifier.background(color = Color.White),
            topBar = {
                AppTopBar(title = "Community & Ideas", isLargeTitle = true)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Loading indicator
                webViewState?.let { state ->
                    if (state.loadingState == WebViewLoadingState.LOADING) {
                        LinearProgressIndicator(
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                // Error message display
                webViewState?.let { state ->
                    if (state.loadingState == WebViewLoadingState.ERROR) {
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = MaterialTheme.colorScheme.errorContainer
                            )
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(
                                    text = "⚠",
                                    style = MaterialTheme.typography.titleLarge
                                )
                                Text(
                                    text = state.errorMessage ?: "Failed to load page",
                                    color = MaterialTheme.colorScheme.onErrorContainer,
                                    modifier = Modifier.weight(1f)
                                )
                            }
                        }
                    }
                }

                // WebView with proper padding
                WebViewComposable(
                    url = currentUrl,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp, vertical = 8.dp),
                    onStateChanged = { state ->
                        webViewState = state
                        // Handle webview state changes
                        println("WebView state changed: $state")
                    },
                    javascriptInterfaces = mapOf(
                        "NativeBridge" to { message ->
                            // Handle JavaScript messages from the web page
                            // JavaScript can call: NativeBridge.postMessage("Hello from JS!")
                            println("Received from JavaScript: $message")
                        }
                    )
                )
            }
        }
    }
}
