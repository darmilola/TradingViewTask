package com.tradingviewtask.app.features.authentication

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import com.tradingviewtask.shared.ui.components.AppScaffold
import com.tradingviewtask.shared.ui.components.AppTopBar

class AuthScreen : Screen {
    @Composable
    override fun Content() {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isLogin by remember { mutableStateOf(true) }

        AppScaffold(
            topBar = {
                AppTopBar(title = if (isLogin) "Sign In" else "Create Account", isLargeTitle = false)
            }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = if (isLogin) "Welcome Back to TradingView" else "Join TradingView Community",
                    style = MaterialTheme.typography.titleLarge
                )
                Spacer(modifier = Modifier.height(24.dp))
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Email") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(16.dp))
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    label = { Text("Password") },
                    visualTransformation = PasswordVisualTransformation(),
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = { /* Handle Auth */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(if (isLogin) "Sign In" else "Sign Up")
                }
                Spacer(modifier = Modifier.height(16.dp))
                TextButton(
                    onClick = { isLogin = !isLogin }
                ) {
                    Text(if (isLogin) "Don't have an account? Sign Up" else "Already have an account? Sign In")
                }
            }
        }
    }
}
