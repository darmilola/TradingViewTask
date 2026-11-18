package com.tradingviewtask.app

import androidx.compose.ui.window.ComposeUIViewController
import platform.UIKit.UIViewController

fun MainViewController(
    onViewControllerCreated: (UIViewController) -> Unit = {}
): UIViewController {
    val viewController = ComposeUIViewController(
        configure = {
        }
    ) {
        AppContent()
    }

    onViewControllerCreated(viewController)

    return viewController
}
