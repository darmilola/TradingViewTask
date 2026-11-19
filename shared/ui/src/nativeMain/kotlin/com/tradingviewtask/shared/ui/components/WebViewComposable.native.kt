package com.tradingviewtask.shared.ui.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.interop.UIKitView
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCSignatureOverride
import platform.CoreGraphics.CGRectMake
import platform.Foundation.NSError
import platform.WebKit.WKNavigation
import platform.WebKit.WKNavigationDelegateProtocol
import platform.WebKit.WKWebView
import platform.WebKit.WKWebViewConfiguration
import platform.darwin.NSObject

@OptIn(ExperimentalForeignApi::class)
@Composable
actual fun PlatformWebView(
    controller: WebViewController,
    modifier: Modifier,
    onStateChanged: ((WebViewState) -> Unit)?
) {
    val navigationDelegate = remember {
        object : NSObject(), WKNavigationDelegateProtocol {
            @ObjCSignatureOverride
            override fun webView(webView: WKWebView, didStartProvisionalNavigation: WKNavigation?) {
                onStateChanged?.invoke(
                    WebViewState(
                        loadingState = WebViewLoadingState.LOADING,
                        currentUrl = webView.URL?.absoluteString,
                        canGoBack = webView.canGoBack,
                        canGoForward = webView.canGoForward
                    )
                )
            }

            @ObjCSignatureOverride
            override fun webView(webView: WKWebView, didFinishNavigation: WKNavigation?) {
                onStateChanged?.invoke(
                    WebViewState(
                        loadingState = WebViewLoadingState.LOADED,
                        currentUrl = webView.URL?.absoluteString,
                        canGoBack = webView.canGoBack,
                        canGoForward = webView.canGoForward
                    )
                )
            }

            @ObjCSignatureOverride
            override fun webView(
                webView: WKWebView,
                didFailNavigation: WKNavigation?,
                withError: NSError
            ) {
                onStateChanged?.invoke(
                    WebViewState(
                        loadingState = WebViewLoadingState.ERROR,
                        currentUrl = webView.URL?.absoluteString,
                        canGoBack = webView.canGoBack,
                        canGoForward = webView.canGoForward,
                        errorMessage = withError.localizedDescription
                    )
                )
            }

            @ObjCSignatureOverride
            override fun webView(
                webView: WKWebView,
                didFailProvisionalNavigation: WKNavigation?,
                withError: NSError
            ) {
                onStateChanged?.invoke(
                    WebViewState(
                        loadingState = WebViewLoadingState.ERROR,
                        currentUrl = webView.URL?.absoluteString,
                        canGoBack = webView.canGoBack,
                        canGoForward = webView.canGoForward,
                        errorMessage = withError.localizedDescription
                    )
                )
            }
        }
    }

    UIKitView(
        modifier = modifier,
        factory = {
            val configuration = WKWebViewConfiguration().apply {
                allowsInlineMediaPlayback = true
            }
            WKWebView(frame = CGRectMake(0.0, 0.0, 0.0, 0.0), configuration = configuration).apply {
                this.navigationDelegate = navigationDelegate
                controller.attachWebView(this)
            }
        },
        update = {
            // no-op
        }
    )
}
