package com.tradingviewtask.app.features.authentication.mcp

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

/**
 * Model Context Protocol (MCP) JSON-RPC 2.0 Response model.
 */
@Serializable
data class McpResponse(
    val jsonrpc: String = "2.0",
    val id: String? = null,
    val result: JsonElement? = null,
    val error: McpError? = null
)

/**
 * MCP Error payload details.
 */
@Serializable
data class McpError(
    val code: Int,
    val message: String,
    val data: JsonElement? = null
)
