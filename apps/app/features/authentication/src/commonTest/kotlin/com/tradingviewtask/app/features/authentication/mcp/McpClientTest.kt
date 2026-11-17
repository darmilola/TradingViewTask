package com.tradingviewtask.app.features.authentication.mcp

import com.tradingviewtask.shared.common.AppResult
import kotlinx.coroutines.runBlocking
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class McpClientTest {

    @Test
    fun testMcpRequestAndResponse() {
        runBlocking {
            val client = TradingViewMcpClient()
            val responseResult = client.sendRequest("test_method")
            assertTrue(responseResult is AppResult.Success)
            val response = (responseResult as AppResult.Success).data
            assertEquals("2.0", response.jsonrpc)
            assertEquals("1", response.id)
        }
    }

    @Test
    fun testExecuteTool() {
        runBlocking {
            val client = TradingViewMcpClient()
            val result = client.executeTool("query_quote")
            assertTrue(result is AppResult.Success)
        }
    }
}
