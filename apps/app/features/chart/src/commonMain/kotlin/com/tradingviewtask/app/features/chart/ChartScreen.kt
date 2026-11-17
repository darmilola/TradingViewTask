package com.tradingviewtask.app.features.chart

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.tradingviewtask.shared.common.Instrument
import com.tradingviewtask.shared.ui.components.AppScaffold
import com.tradingviewtask.shared.ui.components.AppTopBar
import com.tradingviewtask.shared.ui.components.WebViewComposable
import com.tradingviewtask.shared.ui.components.WebViewLoadingState
import com.tradingviewtask.shared.ui.components.WebViewState

class ChartScreen(
    val selectedInstrument: Instrument = Instrument.NVDA,
    val onInstrumentSelect: ((Instrument) -> Unit)? = null
) : Screen {

    @Composable
    override fun Content() {
        var currentInstrument by remember { mutableStateOf(selectedInstrument) }

        LaunchedEffect(selectedInstrument) {
            currentInstrument = selectedInstrument
        }

        val currentUrl = currentInstrument.getWidgetUrl()
        var webViewState by remember { mutableStateOf<WebViewState?>(null) }
        var dropdownExpanded by remember { mutableStateOf(false) }

        AppScaffold(
            modifier = Modifier.background(color = Color.White),
            topBar = {
                AppTopBar(
                    title = "Chart Engine",
                    isLargeTitle = true,
                    actions = {
                        Box {
                            OutlinedButton(
                                onClick = { dropdownExpanded = true },
                                contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp)
                            ) {
                                Text(
                                    text = currentInstrument.ticker,
                                    style = MaterialTheme.typography.titleMedium,
                                    fontWeight = FontWeight.Bold
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = "Select Instrument"
                                )
                            }

                            DropdownMenu(
                                expanded = dropdownExpanded,
                                onDismissRequest = { dropdownExpanded = false },
                                modifier = Modifier.heightIn(max = 350.dp)
                            ) {
                                Instrument.entries.forEach { instrument ->
                                    DropdownMenuItem(
                                        text = {
                                            Column {
                                                Text(
                                                    text = "${instrument.ticker} (${instrument.exchange})",
                                                    style = MaterialTheme.typography.bodyMedium,
                                                    fontWeight = if (instrument == currentInstrument) FontWeight.Bold else FontWeight.Normal
                                                )
                                                Text(
                                                    text = instrument.displayName,
                                                    style = MaterialTheme.typography.bodySmall,
                                                    color = Color.Gray
                                                )
                                            }
                                        },
                                        onClick = {
                                            currentInstrument = instrument
                                            onInstrumentSelect?.invoke(instrument)
                                            dropdownExpanded = false
                                        },
                                        trailingIcon = if (instrument == currentInstrument) {
                                            {
                                                Icon(
                                                    imageVector = Icons.Default.Check,
                                                    contentDescription = "Selected",
                                                    tint = MaterialTheme.colorScheme.primary
                                                )
                                            }
                                        } else null
                                    )
                                }
                            }
                        }
                    }
                )
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
