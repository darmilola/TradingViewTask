package com.tradingviewtask.shared.common

/**
 * Registry of popular financial instruments and their TradingView symbols.
 */
enum class Instrument(
    val symbol: String,
    val ticker: String,
    val displayName: String,
    val exchange: String,
    val assetClass: AssetClass,
) {
    // Stocks
    AAPL("NASDAQ:AAPL", "AAPL", "Apple Inc.", "NASDAQ", AssetClass.STOCK),
    NVDA("NASDAQ:NVDA", "NVDA", "NVIDIA Corporation", "NASDAQ", AssetClass.STOCK),
    TSLA("NASDAQ:TSLA", "TSLA", "Tesla, Inc.", "NASDAQ", AssetClass.STOCK),
    MSFT("NASDAQ:MSFT", "MSFT", "Microsoft Corporation", "NASDAQ", AssetClass.STOCK),
    GOOGL("NASDAQ:GOOGL", "GOOGL", "Alphabet Inc.", "NASDAQ", AssetClass.STOCK),
    AMZN("NASDAQ:AMZN", "AMZN", "Amazon.com, Inc.", "NASDAQ", AssetClass.STOCK),
    META("NASDAQ:META", "META", "Meta Platforms, Inc.", "NASDAQ", AssetClass.STOCK),

    // Crypto
    BTCUSD("BINANCE:BTCUSDT", "BTCUSDT", "Bitcoin / US Dollar", "BINANCE", AssetClass.CRYPTO),
    ETHUSD("BINANCE:ETHUSDT", "ETHUSDT", "Ethereum / US Dollar", "BINANCE", AssetClass.CRYPTO),
    SOLUSD("BINANCE:SOLUSDT", "SOLUSDT", "Solana / US Dollar", "BINANCE", AssetClass.CRYPTO),
    XRPUSD("BINANCE:XRPUSDT", "XRPUSDT", "Ripple / US Dollar", "BINANCE", AssetClass.CRYPTO),

    // Forex
    EURUSD("FX:EURUSD", "EURUSD", "Euro / US Dollar", "FX", AssetClass.FOREX),
    GBPUSD("FX:GBPUSD", "GBPUSD", "British Pound / US Dollar", "FX", AssetClass.FOREX),
    USDJPY("FX:USDJPY", "USDJPY", "US Dollar / Japanese Yen", "FX", AssetClass.FOREX),
    AUDUSD("FX:AUDUSD", "AUDUSD", "Australian Dollar / US Dollar", "FX", AssetClass.FOREX),

    // Indices
    SPX("SP:SPX", "SPX", "S&P 500 Index", "SP", AssetClass.INDEX),
    NDX("NASDAQ:NDX", "NDX", "Nasdaq 100 Index", "NASDAQ", AssetClass.INDEX),
    DJI("DJ:DJI", "DJI", "Dow Jones Industrial Average", "DJ", AssetClass.INDEX),

    // Commodities
    GOLD("OANDA:XAUUSD", "XAUUSD", "Gold / US Dollar", "OANDA", AssetClass.COMMODITY),
    SILVER("OANDA:XAGUSD", "XAGUSD", "Silver / US Dollar", "OANDA", AssetClass.COMMODITY),
    CRUDE_OIL("NYMEX:CL1!", "CL1!", "Crude Oil Futures", "NYMEX", AssetClass.COMMODITY);

    enum class AssetClass {
        STOCK,
        CRYPTO,
        FOREX,
        INDEX,
        COMMODITY
    }

    /**
     * Generates the TradingView widget embed URL for this instrument.
     */
    fun getWidgetUrl(theme: String = "light"): String =
        "https://www.tradingview.com/widgetembed/?symbol=$symbol&theme=$theme"

    companion object {
        /**
         * Finds an Instrument by its full symbol or short ticker (case-insensitive).
         */
        fun fromSymbol(query: String): Instrument? =
            entries.find { 
                it.symbol.equals(query, ignoreCase = true) || 
                it.ticker.equals(query, ignoreCase = true) 
            }

        /**
         * Returns all instruments belonging to the specified asset class.
         */
        fun getByAssetClass(assetClass: AssetClass): List<Instrument> =
            entries.filter { it.assetClass == assetClass }
    }
}
