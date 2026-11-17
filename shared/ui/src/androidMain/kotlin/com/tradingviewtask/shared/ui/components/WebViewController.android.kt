package com.tradingviewtask.shared.ui.components

import android.webkit.CookieManager
import android.webkit.JavascriptInterface
import android.webkit.WebView

actual class WebViewController {
    internal var webView: WebView? = null
    private var pendingUrl: String? = null
    private val jsInterfaces = mutableMapOf<String, (String) -> Unit>()

    internal fun attachWebView(view: WebView) {
        webView = view
        pendingUrl?.let {
            view.loadUrl(it)
            pendingUrl = null
        }
        jsInterfaces.forEach { (name, handler) ->
            view.addJavascriptInterface(WebAppInterface(handler), name)
        }
    }

    actual fun loadUrl(url: String) {
        pendingUrl = url
        webView?.loadUrl(url)
    }

    actual fun evaluateJavaScript(script: String, callback: ((String?) -> Unit)?) {
        webView?.evaluateJavascript(script) { result ->
            callback?.invoke(result)
        } ?: callback?.invoke(null)
    }

    actual fun addJavaScriptInterface(name: String, handler: (String) -> Unit) {
        jsInterfaces[name] = handler
        webView?.addJavascriptInterface(WebAppInterface(handler), name)
    }

    actual fun reload() {
        webView?.reload()
    }

    actual fun goBack() {
        webView?.goBack()
    }

    actual fun goForward() {
        webView?.goForward()
    }

    actual fun canGoBack(): Boolean {
        return webView?.canGoBack() ?: false
    }

    actual fun canGoForward(): Boolean {
        return webView?.canGoForward() ?: false
    }

    actual fun getCurrentUrl(): String? {
        return webView?.url
    }

    actual fun setCookie(url: String, cookie: String) {
        val cookieManager = CookieManager.getInstance()
        cookieManager.setAcceptCookie(true)
        cookieManager.setCookie(url, cookie)
        cookieManager.flush()
    }

    actual fun getCookies(url: String): String? {
        return CookieManager.getInstance().getCookie(url)
    }

    actual fun clearCookies() {
        val cookieManager = CookieManager.getInstance()
        cookieManager.removeAllCookies(null)
        cookieManager.flush()
    }

    actual fun removeCookie(url: String, cookieName: String) {
        val cookieManager = CookieManager.getInstance()
        val cookies = cookieManager.getCookie(url)
        if (cookies != null) {
            val parts = cookies.split(";")
            for (part in parts) {
                val name = part.substringBefore("=").trim()
                if (name == cookieName) {
                    cookieManager.setCookie(url, "$cookieName=; Max-Age=0; path=/")
                }
            }
            cookieManager.flush()
        }
    }

    actual fun dispose() {
        webView?.stopLoading()
        webView?.loadUrl("about:blank")
        webView?.destroy()
        webView = null
    }
}

actual fun createWebViewController(): WebViewController {
    return WebViewController()
}

class WebAppInterface(private val handler: (String) -> Unit) {
    @JavascriptInterface
    fun postMessage(message: String) {
        handler(message)
    }

    @JavascriptInterface
    fun invoke(message: String) {
        handler(message)
    }

    @JavascriptInterface
    fun call(message: String) {
        handler(message)
    }
}
