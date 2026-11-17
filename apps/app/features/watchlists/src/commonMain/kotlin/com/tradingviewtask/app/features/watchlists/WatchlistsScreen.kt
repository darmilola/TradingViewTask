package com.tradingviewtask.app.features.watchlists

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.tradingviewtask.shared.common.Instrument
import com.tradingviewtask.shared.ui.components.AppScaffold
import com.tradingviewtask.shared.ui.components.AppTopBar

data class WatchlistItem(
    val instrument: Instrument,
    val price: String,
    val change: String,
    val isPositive: Boolean
) {
    val symbol: String get() = instrument.ticker
    val name: String get() = instrument.displayName
}

class WatchlistsScreen(
    val selectedInstrument: Instrument? = null,
    val onInstrumentSelect: ((Instrument) -> Unit)? = null
) : Screen {

    @Composable
    override fun Content() {
        var selectedAssetClass by remember { mutableStateOf<Instrument.AssetClass?>(null) }

        val watchListItems = remember {
            Instrument.entries.mapIndexed { index, instrument ->
                val basePrice = when (instrument.assetClass) {
                    Instrument.AssetClass.CRYPTO -> if (instrument == Instrument.BTCUSD) 96450.00 else 3450.00
                    Instrument.AssetClass.FOREX -> 1.0850
                    Instrument.AssetClass.COMMODITY -> if (instrument == Instrument.GOLD) 2650.40 else 30.20
                    Instrument.AssetClass.INDEX -> 5860.20
                    Instrument.AssetClass.STOCK -> 180.00 + (index * 20)
                }
                val changePercent = if (index % 2 == 0) +(1.2 + index * 0.3) else -(0.5 + index * 0.2)
                val isPositive = changePercent >= 0

                val formattedPrice = when (instrument.assetClass) {
                    Instrument.AssetClass.FOREX -> "$$basePrice"
                    else -> "$${basePrice.toInt()}.${(index * 17) % 99}"
                }
                val formattedChange = "${if (isPositive) "+" else ""}${changePercent.toInt()}.${(index * 13) % 99}%"

                WatchlistItem(
                    instrument = instrument,
                    price = formattedPrice,
                    change = formattedChange,
                    isPositive = isPositive
                )
            }
        }

        val filteredItems = remember(selectedAssetClass) {
            if (selectedAssetClass == null) {
                watchListItems
            } else {
                watchListItems.filter { it.instrument.assetClass == selectedAssetClass }
            }
        }

        AppScaffold(
            modifier = Modifier.background(color = Color.White),
            topBar = {
                AppTopBar(title = "Watchlists", isLargeTitle = true)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                // Category Filter Chips
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedAssetClass == null,
                            onClick = { selectedAssetClass = null },
                            label = { Text("All") }
                        )
                    }
                    items(Instrument.AssetClass.entries.toTypedArray()) { assetClass ->
                        FilterChip(
                            selected = selectedAssetClass == assetClass,
                            onClick = { selectedAssetClass = assetClass },
                            label = { Text(assetClass.name.lowercase().replaceFirstChar { it.uppercase() }) }
                        )
                    }
                }

                // List of Watchlist Items
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(filteredItems, key = { it.instrument.symbol }) { item ->
                        val isCurrentSelected = item.instrument == selectedInstrument

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable { onInstrumentSelect?.invoke(item.instrument) },
                            colors = CardDefaults.cardColors(
                                containerColor = if (isCurrentSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f) else Color(0xFFF8F9FA)
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Text(
                                            text = item.instrument.ticker,
                                            style = MaterialTheme.typography.titleMedium,
                                            fontWeight = FontWeight.Bold
                                        )
                                        Spacer(modifier = Modifier.width(6.dp))
                                        Surface(
                                            color = Color.LightGray.copy(alpha = 0.4f),
                                            shape = RoundedCornerShape(4.dp)
                                        ) {
                                            Text(
                                                text = item.instrument.exchange,
                                                style = MaterialTheme.typography.labelSmall,
                                                fontSize = 10.sp,
                                                modifier = Modifier.padding(horizontal = 4.dp, vertical = 2.dp)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Text(
                                        text = item.instrument.displayName,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color.Gray
                                    )
                                }

                                Column(horizontalAlignment = Alignment.End) {
                                    Text(
                                        text = item.price,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.height(2.dp))
                                    Surface(
                                        color = if (item.isPositive) Color(0xFFE6F4EA) else Color(0xFFFCE8E6),
                                        shape = RoundedCornerShape(4.dp)
                                    ) {
                                        Text(
                                            text = item.change,
                                            style = MaterialTheme.typography.labelMedium,
                                            color = if (item.isPositive) Color(0xFF137333) else Color(0xFFC5221F),
                                            fontWeight = FontWeight.Bold,
                                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
