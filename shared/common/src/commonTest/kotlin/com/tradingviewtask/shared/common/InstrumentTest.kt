package com.tradingviewtask.shared.common

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class InstrumentTest {

    @Test
    fun testInstrumentProperties() {
        val aapl = Instrument.AAPL
        assertEquals("NASDAQ:AAPL", aapl.symbol)
        assertEquals("AAPL", aapl.ticker)
        assertEquals("Apple Inc.", aapl.displayName)
        assertEquals("NASDAQ", aapl.exchange)
        assertEquals(Instrument.AssetClass.STOCK, aapl.assetClass)
        assertEquals("https://www.tradingview.com/widgetembed/?symbol=NASDAQ:AAPL&theme=dark", aapl.getWidgetUrl("dark"))
    }

    @Test
    fun testFromSymbolLookup() {
        assertNotNull(Instrument.fromSymbol("NASDAQ:AAPL"))
        assertNotNull(Instrument.fromSymbol("aapl"))
        assertNotNull(Instrument.fromSymbol("BTCUSDT"))
        assertNull(Instrument.fromSymbol("INVALID"))
    }

    @Test
    fun testGetByAssetClass() {
        val stocks = Instrument.getByAssetClass(Instrument.AssetClass.STOCK)
        assert(stocks.isNotEmpty())
        assert(stocks.all { it.assetClass == Instrument.AssetClass.STOCK })
    }
}
