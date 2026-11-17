package com.tradingviewtask.app.features.authentication.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * Model representing an OAuth 2.0 Access Token response from TradingView.
 */
@Serializable
data class OAuthToken(
    @SerialName("access_token") val accessToken: String,
    @SerialName("token_type") val tokenType: String = "Bearer",
    @SerialName("expires_in") val expiresInSeconds: Long = 3600,
    @SerialName("refresh_token") val refreshToken: String? = null,
    @SerialName("scope") val scope: String? = null,
    val issuedAtMillis: Long = 0L
) {
    val isExpired: Boolean
        get() {
            if (expiresInSeconds <= 0) return false
            return (issuedAtMillis + (expiresInSeconds * 1000)) < issuedAtMillis
        }
}
