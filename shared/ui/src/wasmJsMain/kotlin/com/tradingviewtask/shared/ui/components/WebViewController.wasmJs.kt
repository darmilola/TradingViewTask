package com.tradingviewtask.shared.ui.components

import kotlinx.browser.document
import org.w3c.dom.HTMLIFrameElement

actual class WebViewController {
    private var iframe: HTMLIFrameElement? = null
    private var currentUrl: String? = null
    private var onStateChanged: ((WebViewState) -> Unit)? = null

    internal fun attachIframe(element: HTMLIFrameElement, onStateChangedCallback: (WebViewState) -> Unit) {
        this.iframe = element
        this.onStateChanged = onStateChangedCallback

        currentUrl?.let { url ->
            if (url.isNotEmpty() && element.src != url) {
                element.src = url
            }
            onStateChangedCallback(
                WebViewState(
                    loadingState = WebViewLoadingState.LOADED,
                    currentUrl = url,
                    canGoBack = false,
                    canGoForward = false
                )
            )
        }
    }

    internal fun detachIframe() {
        this.iframe = null
        this.onStateChanged = null
    }

    internal fun updateBounds(left: Float, top: Float, width: Float, height: Float) {
        iframe?.setAttribute(
            "style",
            "position: fixed; left: ${left}px; top: ${top}px; width: ${width}px; height: ${height}px; border: none; z-index: 1000;"
        )
    }

    actual fun loadUrl(url: String) {
        currentUrl = url
        iframe?.let { el ->
            if (url.isNotEmpty() && el.src != url) {
                el.src = url
            }
            onStateChanged?.invoke(
                WebViewState(
                    loadingState = WebViewLoadingState.LOADED,
                    currentUrl = url,
                    canGoBack = false,
                    canGoForward = false
                )
            )
        }
    }

    actual fun evaluateJavaScript(script: String, callback: ((String?) -> Unit)?) {
        callback?.invoke(null)
    }

    actual fun addJavaScriptInterface(name: String, handler: (String) -> Unit) {
    }

    actual fun reload() {
        currentUrl?.let { url ->
            iframe?.src = url
        }
    }

    actual fun goBack() {
    }

    actual fun goForward() {
    }

    actual fun canGoBack(): Boolean = false

    actual fun canGoForward(): Boolean = false

    actual fun getCurrentUrl(): String? {
        return iframe?.src ?: currentUrl
    }

    actual fun setCookie(url: String, cookie: String) {
        document.cookie = cookie
    }

    actual fun getCookies(url: String): String? {
        return document.cookie
    }

    actual fun clearCookies() {
    }

    actual fun removeCookie(url: String, cookieName: String) {
        document.cookie = "$cookieName=; expires=Thu, 01 Jan 1970 00:00:00 UTC; path=/;"
    }

    actual fun dispose() {
        detachIframe()
    }
}

actual fun createWebViewController(): WebViewController {
    return WebViewController()
}
