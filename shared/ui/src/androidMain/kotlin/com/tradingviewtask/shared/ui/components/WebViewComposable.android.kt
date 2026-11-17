package com.tradingviewtask.shared.ui.components

import android.graphics.Bitmap
import android.view.ViewGroup
import android.webkit.WebChromeClient
import android.webkit.WebResourceError
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
actual fun PlatformWebView(
    controller: WebViewController,
    modifier: Modifier,
    onStateChanged: ((WebViewState) -> Unit)?
) {
    AndroidView(
        modifier = modifier,
        factory = { context ->
            WebView(context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
                settings.javaScriptEnabled = true
                settings.domStorageEnabled = true
                settings.loadWithOverviewMode = true
                settings.useWideViewPort = true
                settings.cacheMode = WebSettings.LOAD_NO_CACHE

                webViewClient = object : WebViewClient() {
                    override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                        super.onPageStarted(view, url, favicon)
                        onStateChanged?.invoke(
                            WebViewState(
                                loadingState = WebViewLoadingState.LOADING,
                                currentUrl = url,
                                canGoBack = canGoBack(),
                                canGoForward = canGoForward()
                            )
                        )
                    }

                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        onStateChanged?.invoke(
                            WebViewState(
                                loadingState = WebViewLoadingState.LOADED,
                                currentUrl = url,
                                canGoBack = canGoBack(),
                                canGoForward = canGoForward()
                            )
                        )
                    }

                    override fun onReceivedError(
                        view: WebView?,
                        request: WebResourceRequest?,
                        error: WebResourceError?
                    ) {
                        super.onReceivedError(view, request, error)
                        if (request?.isForMainFrame == true) {
                            val errDesc = error?.description?.toString() ?: "Unknown error"
                            if (!errDesc.contains("net::ERR_CACHE_MISS")) {
                                onStateChanged?.invoke(
                                    WebViewState(
                                        loadingState = WebViewLoadingState.ERROR,
                                        currentUrl = request.url.toString(),
                                        canGoBack = canGoBack(),
                                        canGoForward = canGoForward(),
                                        errorMessage = errDesc
                                    )
                                )
                            }
                        }
                    }
                }

                webChromeClient = WebChromeClient()

                controller.attachWebView(this)
            }
        },
        update = { webView ->
            // no-op
        }
    )
}
