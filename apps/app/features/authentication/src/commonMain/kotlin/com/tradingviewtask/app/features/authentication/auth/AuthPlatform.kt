package com.tradingviewtask.app.features.authentication.auth

/**
 * Interface for platform-specific authentication tasks (such as opening Custom Tabs/WebViews or handling OAuth callbacks).
 */
interface AuthPlatform {
    /**
     * Opens the OAuth authorization URL using system browser or Custom Tab.
     */
    fun launchAuthorizationUrl(url: String)

    /**
     * Handles incoming deep-link callback URL and extracts the authorization code.
     */
    fun parseAuthorizationCode(callbackUrl: String): String? {
        if (!callbackUrl.contains("code=")) return null
        return callbackUrl.substringAfter("code=").substringBefore("&")
    }
}

/**
 * Default cross-platform implementation of [AuthPlatform].
 */
class DefaultAuthPlatform : AuthPlatform {
    override fun launchAuthorizationUrl(url: String) {
        println("Opening authorization URL: $url")
    }
}
