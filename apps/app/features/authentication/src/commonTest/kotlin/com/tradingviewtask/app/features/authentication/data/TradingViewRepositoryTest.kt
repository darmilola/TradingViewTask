package com.tradingviewtask.app.features.authentication.data

import com.tradingviewtask.shared.common.AppResult
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class TradingViewRepositoryTest {

    @Test
    fun testRepositoryGetSymbolData() {
        runBlocking {
            val repo = DefaultTradingViewRepository()
            val result = repo.getSymbolData("AAPL")
            assertTrue(result is AppResult.Success)
            val symbolData = (result as AppResult.Success).data
            assertEquals("NASDAQ:AAPL", symbolData.symbol)
            assertTrue(symbolData.isPositive)
        }
    }

    @Test
    fun testRepositoryGetAllSymbols() {
        runBlocking {
            val repo = DefaultTradingViewRepository()
            val result = repo.getAllSymbols()
            assertTrue(result is AppResult.Success)
            val symbols = (result as AppResult.Success).data
            assertTrue(symbols.isNotEmpty())
        }
    }
}
