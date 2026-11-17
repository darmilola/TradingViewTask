package com.tradingviewtask.app.features.authentication.auth

import com.tradingviewtask.shared.common.AppResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
 * Repository interface for managing authentication tokens.
 */
interface TokenRepository {
    val currentToken: StateFlow<OAuthToken?>
    val isAuthenticated: Boolean

    suspend fun saveToken(token: OAuthToken): AppResult<Unit>
    suspend fun getToken(): OAuthToken?
    suspend fun clearToken(): AppResult<Unit>
}

/**
 * In-memory implementation of [TokenRepository].
 */
class InMemoryTokenRepository : TokenRepository {
    private val _currentToken = MutableStateFlow<OAuthToken?>(null)
    override val currentToken: StateFlow<OAuthToken?> = _currentToken.asStateFlow()

    override val isAuthenticated: Boolean
        get() = _currentToken.value != null

    override suspend fun saveToken(token: OAuthToken): AppResult<Unit> {
        _currentToken.value = token
        return AppResult.Success(Unit)
    }

    override suspend fun getToken(): OAuthToken? = _currentToken.value

    override suspend fun clearToken(): AppResult<Unit> {
        _currentToken.value = null
        return AppResult.Success(Unit)
    }
}
