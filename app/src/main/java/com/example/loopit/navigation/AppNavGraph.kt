package com.example.loopit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.loopit.ui.screens.LoginScreen
import com.example.loopit.ui.screens.CreateAccountScreen
import com.example.loopit.ui.screens.ForgotPasswordScreen

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {

        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginClick = { _, _ ->
                    // Login functionality will be added later
                },
                onForgotPasswordClick = {
                    navController.navigate(Routes.FORGOT_PASSWORD)
                },
                onCreateAccountClick = {
                    navController.navigate(Routes.CREATE_ACCOUNT)
                }
            )
        }

        composable(Routes.CREATE_ACCOUNT) {
            CreateAccountScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onLoginClick = {
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                onCreateAccountClick = { _, _, _ ->
                    // Account creation will be added later
                }
            )
        }

        composable(Routes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSendResetLink = { _ ->
                    // Password reset functionality will be added later
                }
            )
        }
    }
}