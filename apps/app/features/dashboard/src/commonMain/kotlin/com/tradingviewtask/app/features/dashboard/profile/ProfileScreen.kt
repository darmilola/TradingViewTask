package com.tradingviewtask.app.features.dashboard.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import com.tradingviewtask.shared.ui.components.AppScaffold
import com.tradingviewtask.shared.ui.components.AppTopBar

class ProfileScreen(
    private val onRateUsClick: () -> Unit = {},
    private val onHelpCenterClick: () -> Unit = {},
    private val onAboutClick: () -> Unit = {},
    private val onSignOutClick: () -> Unit = {}
) : Screen {

    @Composable
    override fun Content() {
        ProfileContent(
            onRateUsClick = onRateUsClick,
            onHelpCenterClick = onHelpCenterClick,
            onAboutClick = onAboutClick,
            onSignOutClick = onSignOutClick
        )
    }
}

@Composable
fun ProfileContent(
    modifier: Modifier = Modifier,
    onRateUsClick: () -> Unit = {},
    onHelpCenterClick: () -> Unit = {},
    onAboutClick: () -> Unit = {},
    onSignOutClick: () -> Unit = {}
) {
    AppScaffold(
        modifier = modifier.background(color = Color.White),
        topBar = {
            AppTopBar(title = "Profile", isLargeTitle = true)
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(Color.White)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            ProfileMenuItem(
                icon = RateUsIcon,
                title = "Rate us",
                onClick = onRateUsClick
            )

            ProfileMenuItem(
                icon = HelpCenterIcon,
                title = "Help Center",
                onClick = onHelpCenterClick
            )

            ProfileMenuItem(
                icon = AboutIcon,
                title = "About",
                showChevron = true,
                onClick = onAboutClick
            )

            Spacer(modifier = Modifier.height(16.dp))

            ProfileMenuItem(
                icon = SignOutIcon,
                title = "Sign Out",
                titleColor = Color(0xFFE53935),
                iconTint = Color(0xFFE53935),
                onClick = onSignOutClick
            )
        }
    }
}

@Composable
private fun ProfileMenuItem(
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    titleColor: Color = Color.Black,
    iconTint: Color = titleColor,
    showChevron: Boolean = false,
    showDivider: Boolean = true
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title,
                tint = iconTint,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Text(
                text = title,
                style = MaterialTheme.typography.bodyLarge.copy(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                ),
                color = titleColor,
                modifier = Modifier.weight(1f)
            )

            if (showChevron) {
                Icon(
                    imageVector = ChevronRightIcon,
                    contentDescription = null,
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }
        }

        if (showDivider) {
            HorizontalDivider(
                modifier = Modifier.padding(start = 56.dp),
                color = Color(0xFFF0F0F0),
                thickness = 1.dp
            )
        }
    }
}

private val RateUsIcon: ImageVector
    get() = ImageVector.Builder(
        name = "RateUs",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        // Speech bubble path
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 1.8f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(20f, 4f)
            lineTo(4f, 4f)
            curveTo(2.9f, 4f, 2f, 4.9f, 2f, 6f)
            lineTo(2f, 15f)
            curveTo(2f, 16.1f, 2.9f, 17f, 4f, 17f)
            lineTo(7f, 17f)
            lineTo(4f, 21f)
            lineTo(10.5f, 17f)
            lineTo(20f, 17f)
            curveTo(21.1f, 17f, 22f, 16.1f, 22f, 15f)
            lineTo(22f, 6f)
            curveTo(22f, 4.9f, 21.1f, 4f, 20f, 4f)
            close()
        }
        // Star inside
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 1.5f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(12f, 7f)
            lineTo(13.1f, 9.3f)
            lineTo(15.5f, 9.6f)
            lineTo(13.7f, 11.3f)
            lineTo(14.2f, 13.7f)
            lineTo(12f, 12.5f)
            lineTo(9.8f, 13.7f)
            lineTo(10.3f, 11.3f)
            lineTo(8.5f, 9.6f)
            lineTo(10.9f, 9.3f)
            close()
        }
    }.build()

private val HelpCenterIcon: ImageVector
    get() = ImageVector.Builder(
        name = "HelpCenter",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        // Circle outline
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 1.8f
        ) {
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
            curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
            curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
            close()
        }
        // Question mark curve
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 1.8f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(9.5f, 9f)
            curveTo(9.5f, 7.6f, 10.6f, 6.5f, 12f, 6.5f)
            curveTo(13.4f, 6.5f, 14.5f, 7.6f, 14.5f, 9f)
            curveTo(14.5f, 10.2f, 13.5f, 11f, 12.5f, 11.8f)
            curveTo(12f, 12.2f, 12f, 12.8f, 12f, 13.5f)
        }
        // Dot under question mark
        path(
            fill = SolidColor(Color.Black)
        ) {
            moveTo(12f, 16f)
            curveTo(11.45f, 16f, 11f, 16.45f, 11f, 17f)
            curveTo(11f, 17.55f, 11.45f, 18f, 12f, 18f)
            curveTo(12.55f, 18f, 13f, 17.55f, 13f, 17f)
            curveTo(13f, 16.45f, 12.55f, 16f, 12f, 16f)
            close()
        }
    }.build()

private val AboutIcon: ImageVector
    get() = ImageVector.Builder(
        name = "About",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        // Circle outline
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 1.8f
        ) {
            moveTo(12f, 2f)
            curveTo(6.48f, 2f, 2f, 6.48f, 2f, 12f)
            curveTo(2f, 17.52f, 6.48f, 22f, 12f, 22f)
            curveTo(17.52f, 22f, 22f, 17.52f, 22f, 12f)
            curveTo(22f, 6.48f, 17.52f, 2f, 12f, 2f)
            close()
        }
        // Dot at top of 'i'
        path(
            fill = SolidColor(Color.Black)
        ) {
            moveTo(12f, 6.5f)
            curveTo(11.45f, 6.5f, 11f, 6.95f, 11f, 7.5f)
            curveTo(11f, 8.05f, 11.45f, 8.5f, 12f, 8.5f)
            curveTo(12.55f, 8.5f, 13f, 8.05f, 13f, 7.5f)
            curveTo(13f, 6.95f, 12.55f, 6.5f, 12f, 6.5f)
            close()
        }
        // Vertical line of 'i'
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 1.8f,
            strokeLineCap = StrokeCap.Round
        ) {
            moveTo(12f, 11f)
            lineTo(12f, 17f)
        }
    }.build()

private val SignOutIcon: ImageVector
    get() = ImageVector.Builder(
        name = "SignOut",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        // Box bracket open to left
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 1.8f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(14f, 4f)
            lineTo(18f, 4f)
            curveTo(19.1f, 4f, 20f, 4.9f, 20f, 6f)
            lineTo(20f, 18f)
            curveTo(20f, 19.1f, 19.1f, 20f, 18f, 20f)
            lineTo(14f, 20f)
        }
        // Arrow pointing left
        path(
            stroke = SolidColor(Color.Black),
            strokeLineWidth = 1.8f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(15f, 12f)
            lineTo(4f, 12f)
            moveTo(8f, 8f)
            lineTo(4f, 12f)
            lineTo(8f, 16f)
        }
    }.build()

private val ChevronRightIcon: ImageVector
    get() = ImageVector.Builder(
        name = "ChevronRight",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            stroke = SolidColor(Color.Gray),
            strokeLineWidth = 1.8f,
            strokeLineCap = StrokeCap.Round,
            strokeLineJoin = StrokeJoin.Round
        ) {
            moveTo(9f, 6f)
            lineTo(15f, 12f)
            lineTo(9f, 18f)
        }
    }.build()
