package com.tradingviewtask.app.features.authentication.data

import com.tradingviewtask.shared.common.AppResult
import com.tradingviewtask.shared.common.Instrument

/**
 * Repository for fetching TradingView market symbol data and user portfolio info.
 */
interface TradingViewRepository {
    suspend fun getSymbolData(symbol: String): AppResult<SymbolData>
    suspend fun getAllSymbols(): AppResult<List<SymbolData>>
}

/**
 * Default implementation of [TradingViewRepository].
 */
class DefaultTradingViewRepository : TradingViewRepository {

    override suspend fun getSymbolData(symbol: String): AppResult<SymbolData> {
        val instrument = Instrument.fromSymbol(symbol) ?: Instrument.AAPL
        val mockData = SymbolData.fromInstrument(instrument, price = 224.50, change = 3.25)
        return AppResult.Success(mockData)
    }

    override suspend fun getAllSymbols(): AppResult<List<SymbolData>> {
        val mockList = Instrument.entries.mapIndexed { index, instrument ->
            val basePrice = 100.0 + (index * 25.5)
            val change = if (index % 2 == 0) (index + 1) * 0.8 else -(index + 1) * 0.5
            SymbolData.fromInstrument(instrument, price = basePrice, change = change)
        }
        return AppResult.Success(mockList)
    }
}
