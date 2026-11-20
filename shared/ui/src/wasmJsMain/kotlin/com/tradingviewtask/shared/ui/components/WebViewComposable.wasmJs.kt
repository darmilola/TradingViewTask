package com.tradingviewtask.shared.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
actual fun PlatformWebView(
    controller: WebViewController,
    modifier: Modifier,
    onStateChanged: ((WebViewState) -> Unit)?
) {
    LaunchedEffect(Unit) {
        onStateChanged?.invoke(
            WebViewState(
                loadingState = WebViewLoadingState.LOADED,
                currentUrl = controller.getCurrentUrl()
            )
        )
    }

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "WebView (Wasm/JS Web)")
    }
}
