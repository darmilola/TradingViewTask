package com.tradingviewtask.shared.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.tradingviewtask.shared.ui.theme.AppThemeDefaults

/**
 * Visuals for the custom AppScaffold snackbar to carry success/failure information.
 */
private class AppSnackbarVisuals(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = false,
    override val duration: SnackbarDuration = SnackbarDuration.Short,
    val isSuccess: Boolean = true
) : SnackbarVisuals

/**
 * A custom Scaffold utility that handles system insets (status bar, navigation bar, cutouts)
 * and provides a consistent structure for screens across the app.
 */
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    snackbarHost: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = AppThemeDefaults.colors.background,
    contentColor: Color = contentColorFor(containerColor),
    // By default, handle all system bars and display cutouts.
    // We union them to ensure a comprehensive safe area.
    contentWindowInsets: WindowInsets = WindowInsets.systemBars.union(WindowInsets.displayCutout),
    isOnline: Boolean = true,
    isLoading: Boolean = false,
    loadingContent: @Composable (PaddingValues) -> Unit = { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.TopCenter
        ) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth().height(2.dp),
                color = Color.Black,
                trackColor = Color.Transparent
            )
        }
    },
    error: String? = null,
    onRetry: (() -> Unit)? = null,
    errorContent: @Composable (PaddingValues, String, (() -> Unit)?) -> Unit = { padding, msg, retry ->
        ErrorScreen(
            message = msg,
            onRetry = retry,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        )
    },
    snackbarMessage: String? = null,
    isSuccessSnackbar: Boolean = true,
    snackbarActionLabel: String? = null,
    onSnackbarAction: (() -> Unit)? = null,
    onSnackbarDismiss: (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(snackbarMessage, isSuccessSnackbar, snackbarActionLabel) {
        snackbarMessage?.let {
            val result = snackbarHostState.showSnackbar(
                visuals = AppSnackbarVisuals(
                    message = it,
                    isSuccess = isSuccessSnackbar,
                    actionLabel = snackbarActionLabel,
                    duration = SnackbarDuration.Short
                )
            )
            when (result) {
                SnackbarResult.ActionPerformed -> onSnackbarAction?.invoke()
                SnackbarResult.Dismissed -> onSnackbarDismiss?.invoke()
            }
        }
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            Column(modifier = Modifier.background(Color.White)) {
                topBar()
                OfflineBanner(isOnline = isOnline)
            }
        },
        bottomBar = bottomBar,
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data ->
                val isSuccess = (data.visuals as? AppSnackbarVisuals)?.isSuccess ?: isSuccessSnackbar
                Snackbar(
                    snackbarData = data,
                    containerColor = if (isSuccess) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error,
                    contentColor = Color.White,
                    actionColor = Color.White
                )
            }
        },
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
        contentWindowInsets = contentWindowInsets,
        content = { paddingValues ->
            // Use consumeWindowInsets to prevent double padding in nested Scaffolds
            Box(Modifier.consumeWindowInsets(paddingValues)) {
                when {
                    isLoading -> loadingContent(paddingValues)
                    error != null -> errorContent(paddingValues, error, onRetry)
                    else -> content(paddingValues)
                }
            }
        }
    )
}

@Composable
private fun ErrorScreen(
    message: String,
    onRetry: (() -> Unit)?,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = message,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.error
        )
        if (onRetry != null) {
            Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

@Composable
private fun OfflineBanner(isOnline: Boolean) {
    AnimatedVisibility(
        visible = !isOnline,
        enter = expandVertically(),
        exit = shrinkVertically()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red.copy(alpha = 0.8f))
                .padding(vertical = 4.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "No internet connection",
                color = Color.White,
                fontSize = 12.dp.value.sp,
                textAlign = TextAlign.Center
            )
        }
    }
}

/**
 * A wrapper for screen content that automatically handles system insets
 * when you don't need a full Scaffold.
 */
@Composable
fun AppScreen(
    modifier: Modifier = Modifier,
    backgroundColor: Color = AppThemeDefaults.colors.background,
    handleStatusBars: Boolean = true,
    handleNavigationBars: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    val topInset = if (handleStatusBars) WindowInsets.statusBars else WindowInsets(0, 0, 0, 0)
    val bottomInset = if (handleNavigationBars) WindowInsets.navigationBars else WindowInsets(0, 0, 0, 0)
    
    val windowInsets = topInset.union(bottomInset).union(WindowInsets.displayCutout)

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .windowInsetsPadding(windowInsets)
            .consumeWindowInsets(windowInsets)
    ) {
        content()
    }
}
