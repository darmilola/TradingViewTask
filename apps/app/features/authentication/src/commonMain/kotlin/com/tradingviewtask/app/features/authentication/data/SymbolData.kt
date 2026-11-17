package com.tradingviewtask.app.features.authentication.data

import com.tradingviewtask.shared.common.Instrument
import kotlinx.serialization.Serializable

/**
 * Market data model representing financial symbol information fetched from TradingView.
 */
@Serializable
data class SymbolData(
    val symbol: String,
    val name: String,
    val price: Double,
    val change: Double,
    val changePercent: Double,
    val exchange: String,
    val assetClass: String,
    val volume: Long = 0L,
    val high24h: Double = 0.0,
    val low24h: Double = 0.0
) {
    val isPositive: Boolean get() = change >= 0.0

    companion object {
        fun fromInstrument(instrument: Instrument, price: Double = 100.0, change: Double = 1.5): SymbolData {
            return SymbolData(
                symbol = instrument.symbol,
                name = instrument.displayName,
                price = price,
                change = change,
                changePercent = (change / price) * 100,
                exchange = instrument.exchange,
                assetClass = instrument.assetClass.name
            )
        }
    }
}
