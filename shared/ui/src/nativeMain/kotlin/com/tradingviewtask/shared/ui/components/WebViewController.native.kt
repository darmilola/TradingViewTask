package com.tradingviewtask.shared.ui.components

actual class WebViewController {
    private var currentUrl: String? = null

    actual fun loadUrl(url: String) {
        currentUrl = url
    }

    actual fun evaluateJavaScript(script: String, callback: ((String?) -> Unit)?) {
        callback?.invoke(null)
    }

    actual fun addJavaScriptInterface(name: String, handler: (String) -> Unit) {
    }

    actual fun reload() {
    }

    actual fun goBack() {
    }

    actual fun goForward() {
    }

    actual fun canGoBack(): Boolean {
        return false
    }

    actual fun canGoForward(): Boolean {
        return false
    }

    actual fun getCurrentUrl(): String? {
        return currentUrl
    }

    actual fun setCookie(url: String, cookie: String) {
    }

    actual fun getCookies(url: String): String? {
        return null
    }

    actual fun clearCookies() {
    }

    actual fun removeCookie(url: String, cookieName: String) {
    }

    actual fun dispose() {
    }
}

actual fun createWebViewController(): WebViewController {
    return WebViewController()
}
