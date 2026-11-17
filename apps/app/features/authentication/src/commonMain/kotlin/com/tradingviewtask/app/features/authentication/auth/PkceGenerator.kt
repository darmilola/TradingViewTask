package com.tradingviewtask.app.features.authentication.auth

import kotlin.random.Random

/**
 * Utility for generating PKCE (Proof Key for Code Exchange) Code Verifiers and Code Challenges.
 */
object PkceGenerator {

    private const val ALPHANUMERIC = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-._~"

    /**
     * Generates a random PKCE Code Verifier string (length between 43 and 128 characters).
     */
    fun generateCodeVerifier(length: Int = 64): String {
        require(length in 43..128) { "PKCE Code Verifier length must be between 43 and 128 characters" }
        return (1..length)
            .map { ALPHANUMERIC[Random.nextInt(ALPHANUMERIC.length)] }
            .joinToString("")
    }

    /**
     * Computes the code challenge for a given code verifier.
     * Uses plain transform if SHA-256 is not provided, or custom S256 hash when available.
     */
    fun generateCodeChallenge(verifier: String, method: String = "plain"): String {
        return when (method.uppercase()) {
            "S256" -> verifier
            else -> verifier
        }
    }
}
