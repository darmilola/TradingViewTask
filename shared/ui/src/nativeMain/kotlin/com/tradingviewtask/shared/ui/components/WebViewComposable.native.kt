package com.tradingviewtask.shared.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
actual fun PlatformWebView(
    controller: WebViewController,
    modifier: Modifier,
    onStateChanged: ((WebViewState) -> Unit)?
) {
}
