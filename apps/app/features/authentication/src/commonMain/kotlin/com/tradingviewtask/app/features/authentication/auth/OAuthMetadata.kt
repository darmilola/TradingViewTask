package com.tradingviewtask.app.features.authentication.auth

import kotlinx.serialization.Serializable

/**
 * Metadata configuration for TradingView OAuth 2.0 authorization code flow with PKCE.
 */
@Serializable
data class OAuthMetadata(
    val clientId: String = "tradingview_app_client",
    val authorizationEndpoint: String = "https://www.tradingview.com/oauth/authorize",
    val tokenEndpoint: String = "https://www.tradingview.com/oauth/token",
    val redirectUri: String = "tradingviewapp://oauth/callback",
    val scopes: List<String> = listOf("read", "trade", "quotes", "chart"),
    val state: String = ""
) {
    val scopeString: String get() = scopes.joinToString(" ")
}
