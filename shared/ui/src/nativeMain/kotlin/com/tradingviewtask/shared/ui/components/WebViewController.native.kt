package com.tradingviewtask.shared.ui.components

import platform.Foundation.NSURL
import platform.Foundation.NSURLRequest
import platform.WebKit.WKScriptMessage
import platform.WebKit.WKScriptMessageHandlerProtocol
import platform.WebKit.WKUserContentController
import platform.WebKit.WKWebView
import platform.darwin.NSObject

actual class WebViewController {
    private var webView: WKWebView? = null
    private var currentUrl: String? = null
    private val pendingInterfaces = mutableMapOf<String, (String) -> Unit>()
    private val scriptMessageHandlers = mutableMapOf<String, WKScriptMessageHandlerProtocol>()

    fun attachWebView(webView: WKWebView) {
        this.webView = webView
        pendingInterfaces.forEach { (name, handler) ->
            addJavaScriptInterface(name, handler)
        }
        pendingInterfaces.clear()

        currentUrl?.let { url ->
            loadUrl(url)
        }
    }

    actual fun loadUrl(url: String) {
        currentUrl = url
        val targetWebView = webView
        if (targetWebView != null) {
            val nsUrl = NSURL.URLWithString(url)
            if (nsUrl != null) {
                val request = NSURLRequest.requestWithURL(nsUrl)
                targetWebView.loadRequest(request)
            }
        }
    }

    actual fun evaluateJavaScript(script: String, callback: ((String?) -> Unit)?) {
        webView?.evaluateJavaScript(script) { result, _ ->
            callback?.invoke(result?.toString())
        }
    }

    actual fun addJavaScriptInterface(name: String, handler: (String) -> Unit) {
        val targetWebView = webView
        if (targetWebView == null) {
            pendingInterfaces[name] = handler
            return
        }

        val contentController = targetWebView.configuration.userContentController
        if (!scriptMessageHandlers.containsKey(name)) {
            val scriptHandler = object : NSObject(), WKScriptMessageHandlerProtocol {
                override fun userContentController(
                    userContentController: WKUserContentController,
                    didReceiveScriptMessage: WKScriptMessage
                ) {
                    val bodyString = didReceiveScriptMessage.body.toString()
                    handler.invoke(bodyString)
                }
            }
            scriptMessageHandlers[name] = scriptHandler
            contentController.addScriptMessageHandler(scriptHandler, name = name)

            val polyfill = """
                window.$name = {
                    postMessage: function(message) {
                        window.webkit.messageHandlers.$name.postMessage(message);
                    }
                };
            """.trimIndent()
            evaluateJavaScript(polyfill, null)
        }
    }

    actual fun reload() {
        webView?.reload()
    }

    actual fun goBack() {
        if (canGoBack()) {
            webView?.goBack()
        }
    }

    actual fun goForward() {
        if (canGoForward()) {
            webView?.goForward()
        }
    }

    actual fun canGoBack(): Boolean {
        return webView?.canGoBack ?: false
    }

    actual fun canGoForward(): Boolean {
        return webView?.canGoForward ?: false
    }

    actual fun getCurrentUrl(): String? {
        return webView?.URL?.absoluteString ?: currentUrl
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
        val targetWebView = webView
        if (targetWebView != null) {
            val contentController = targetWebView.configuration.userContentController
            scriptMessageHandlers.keys.forEach { name ->
                contentController.removeScriptMessageHandlerForName(name)
            }
        }
        scriptMessageHandlers.clear()
        pendingInterfaces.clear()
        webView = null
    }
}

actual fun createWebViewController(): WebViewController {
    return WebViewController()
}
