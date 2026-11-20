package com.tradingviewtask.shared.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInWindow
import kotlinx.browser.document
import org.w3c.dom.HTMLIFrameElement

@Composable
actual fun PlatformWebView(
    controller: WebViewController,
    modifier: Modifier,
    onStateChanged: ((WebViewState) -> Unit)?
) {
    DisposableEffect(controller) {
        val iframe = (document.createElement("iframe") as HTMLIFrameElement).apply {
            setAttribute(
                "style",
                "position: fixed; border: none; z-index: 1000;"
            )
        }

        controller.attachIframe(iframe) { newState ->
            onStateChanged?.invoke(newState)
        }

        document.body?.appendChild(iframe)

        onDispose {
            iframe.remove()
            controller.detachIframe()
        }
    }
}
