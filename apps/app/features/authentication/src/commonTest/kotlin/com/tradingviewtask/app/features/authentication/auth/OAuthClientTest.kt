package com.tradingviewtask.app.features.authentication.auth

import com.tradingviewtask.shared.common.AppResult
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class OAuthClientTest {

    @Test
    fun testPkceGenerator() {
        val verifier = PkceGenerator.generateCodeVerifier(64)
        assertEquals(64, verifier.length)
        val challenge = PkceGenerator.generateCodeChallenge(verifier)
        assertNotNull(challenge)
    }

    @Test
    fun testOAuthClientBuildUrl() {
        val client = OAuthClient()
        val url = client.buildAuthorizationUrl("test_state")
        assertTrue(url.contains("response_type=code"))
        assertTrue(url.contains("client_id=tradingview_app_client"))
        assertTrue(url.contains("state=test_state"))
        assertNotNull(client.currentCodeVerifier)
    }

    @Test
    fun testTokenExchangeAndRefresh() {
        runBlocking {
            val client = OAuthClient()
            val exchangeResult = client.exchangeCodeForToken("valid_code_123")
            assertTrue(exchangeResult is AppResult.Success)
            val token = (exchangeResult as AppResult.Success).data
            assertEquals("Bearer", token.tokenType)

            val refreshResult = client.refreshToken()
            assertTrue(refreshResult is AppResult.Success)

            val logoutResult = client.logout()
            assertTrue(logoutResult is AppResult.Success)
        }
    }
}
