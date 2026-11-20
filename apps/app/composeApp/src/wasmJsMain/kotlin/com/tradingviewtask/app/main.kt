package com.tradingviewtask.app

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.window.ComposeViewport
import com.tradingviewtask.shared.ui.theme.AppTheme
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLElement
import org.w3c.dom.url.URLSearchParams

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    try {
        val params = URLSearchParams(window.location.search.toJsString())
        val rawTarget = params.get("deepLink") ?: window.location.pathname

        val deepLink = when {
            rawTarget.startsWith("tradingviewtask://") -> rawTarget
            else -> null
        }

        val deepLinkHandler = object : DeepLinkHandler {
            override fun handleDeepLink(url: String) {
                // handle deep link
            }
        }

        val root = document.getElementById("compose-target") as? HTMLElement
            ?: error("compose-target element not found")
        ComposeViewport(root) {
            AppRoot(deepLinkHandler)
        }
    } catch (t: Throwable) {
        t.printStackTrace()
        showErrorOnPage(t)
    }
}

@Composable
fun AppRoot(deepLinkHandler: DeepLinkHandler) {
    AppTheme {
        BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Surface(
                    modifier = Modifier
                        .fillMaxHeight()
                        .fillMaxWidth(),
                    color = MaterialTheme.colorScheme.background,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ) {
                    AppContent(
                        deepLinkHandler = deepLinkHandler
                    )
                }
            }
        }
    }
}

private fun showErrorOnPage(t: Throwable) {
    val errorDiv = document.createElement("div")

    errorDiv.setAttribute(
        "style",
        """
        position:fixed;
        inset:0;
        overflow:auto;
        background:#fafafa;
        color:#b00020;
        font-family:monospace;
        padding:24px;
        z-index:999999;
        """.trimIndent()
    )

    errorDiv.innerHTML = """
        <h2>TradingViewTask failed to start</h2>

        <p><strong>${t::class.simpleName}</strong></p>

        <p>${t.message ?: "No error message"}</p>

        <hr>

        <pre>${t.stackTraceToString()}</pre>
    """.trimIndent()

    document.body?.appendChild(errorDiv)
}
