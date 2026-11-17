package com.tradingviewtask.app.features.authentication.auth

import com.tradingviewtask.shared.common.AppResult

/**
 * OAuth 2.0 client managing authentication state, login flow, and token exchange.
 */
class OAuthClient(
    val metadata: OAuthMetadata = OAuthMetadata(),
    private val tokenRepository: TokenRepository = InMemoryTokenRepository()
) {
    var currentCodeVerifier: String? = null
        private set

    /**
     * Builds the full authorization URL for opening in a web view or external browser.
     */
    fun buildAuthorizationUrl(state: String = metadata.state): String {
        val verifier = PkceGenerator.generateCodeVerifier()
        currentCodeVerifier = verifier
        val challenge = PkceGenerator.generateCodeChallenge(verifier)

        return "${metadata.authorizationEndpoint}?" +
                "response_type=code" +
                "&client_id=${metadata.clientId}" +
                "&redirect_uri=${metadata.redirectUri}" +
                "&scope=${metadata.scopeString}" +
                "&state=$state" +
                "&code_challenge=$challenge" +
                "&code_challenge_method=S256"
    }

    /**
     * Exchanges an authorization code for an OAuth access token.
     */
    suspend fun exchangeCodeForToken(authorizationCode: String): AppResult<OAuthToken> {
        if (authorizationCode.isBlank()) {
            return AppResult.Error("Authorization code cannot be blank")
        }

        // Mock/simulated successful token exchange
        val token = OAuthToken(
            accessToken = "tv_access_token_${authorizationCode.take(8)}",
            refreshToken = "tv_refresh_token_sample",
            expiresInSeconds = 3600
        )
        tokenRepository.saveToken(token)
        return AppResult.Success(token)
    }

    /**
     * Refreshes an expired access token using the refresh token.
     */
    suspend fun refreshToken(): AppResult<OAuthToken> {
        val existingToken = tokenRepository.getToken()
            ?: return AppResult.Error("No existing token found to refresh")

        val refreshToken = existingToken.refreshToken
            ?: return AppResult.Error("No refresh token available")

        val newToken = OAuthToken(
            accessToken = "tv_refreshed_access_token_${refreshToken.take(8)}",
            refreshToken = refreshToken,
            expiresInSeconds = 3600
        )
        tokenRepository.saveToken(newToken)
        return AppResult.Success(newToken)
    }

    /**
     * Logs out the user and clears stored authentication tokens.
     */
    suspend fun logout(): AppResult<Unit> {
        return tokenRepository.clearToken()
    }
}
