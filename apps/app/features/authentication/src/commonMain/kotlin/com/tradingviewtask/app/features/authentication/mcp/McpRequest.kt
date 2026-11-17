package com.tradingviewtask.app.features.authentication.mcp

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonObject

/**
 * Model Context Protocol (MCP) JSON-RPC 2.0 Request model.
 */
@Serializable
data class McpRequest(
    val jsonrpc: String = "2.0",
    val id: String,
    val method: String,
    val params: JsonObject? = null
)
