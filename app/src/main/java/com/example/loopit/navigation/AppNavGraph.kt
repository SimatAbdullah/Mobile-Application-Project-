
package com.example.loopit.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.loopit.ui.screens.LoginScreen
import com.example.loopit.ui.screens.CreateAccountScreen
import com.example.loopit.ui.screens.ForgotPasswordScreen
import com.example.loopit.ui.screens.HomeRoute
import com.example.loopit.ui.screens.ProfileRoute
import com.example.loopit.ui.screens.EditProfileScreen

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {

        // 1. Login
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginClick = { _, _ ->
                    // Authentication will be added later.
                },
                onForgotPasswordClick = {
                    navController.navigate(Routes.FORGOT_PASSWORD)
                },
                onCreateAccountClick = {
                    navController.navigate(Routes.CREATE_ACCOUNT)
                }
            )
        }

        // 2. Create Account
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
                    // Account creation will be added later.
                }
            )
        }

        // 3. Forgot Password
        composable(Routes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSendResetLink = { _ ->
                    // Password reset will be added later.
                }
            )
        }

        // 4. Home - Reem
        composable(Routes.HOME) {
            HomeRoute(
                onNotificationsClick = {
                    // Connect when Notifications is ready.
                },
                onRecordRecyclingClick = {
                    // Connect when Record Recycling is ready.
                },
                onIdentifyClick = {
                    // Connect when Identify Device is ready.
                },
                onPointsClick = {
                    // Connect when Your Impact is registered.
                },
                onRewardsClick = {
                    // Connect when Rewards is ready.
                },
                onChecklistClick = {
                    // Connect checklist later.
                },
                onViewStatisticsClick = {
                    // Connect Your Impact later.
                },
                onMapTabClick = {
                    // Connect Collection Points later.
                },
                onScanTabClick = {
                    // Connect Identify Device later.
                },
                onProfileTabClick = {
                    navController.navigate(Routes.PROFILE) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // 5. Profile - Reem
        composable(Routes.PROFILE) {
            ProfileRoute(
                onHomeTabClick = {
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.HOME) {
                            inclusive = false
                        }
                        launchSingleTop = true
                    }
                },
                onMapTabClick = {
                    // Connect Collection Points later.
                },
                onScanTabClick = {
                    // Connect Identify Device later.
                },
                onRewardsTabClick = {
                    // Connect Rewards later.
                },
                onStatisticsClick = {
                    // Connect Your Impact later.
                },
                onRecyclingHistoryClick = {
                    // Connect Miad's screen later.
                },
                onRewardsClick = {
                    // Connect Rewards later.
                },
                onEditProfileClick = {
                    navController.navigate(Routes.EDIT_PROFILE)
                },
                onNotificationsClick = {
                    // Connect Notifications later.
                },
                onAccountSettingsClick = {
                    // Connect Account Settings later.
                },
                onConfirmLogoutClick = {
                    // Navigation demo only.
                    navController.navigate(Routes.LOGIN) {
                        popUpTo(Routes.HOME) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        // 6. Edit Profile - Dalia
        composable(Routes.EDIT_PROFILE) {
            EditProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSaveClick = { _, _ ->
                    // Database saving will be added later.
                    navController.popBackStack()
                },
                onCancelClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}
