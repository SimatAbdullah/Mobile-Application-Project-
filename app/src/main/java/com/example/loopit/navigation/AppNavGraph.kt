
package com.example.loopit.navigation

import android.net.Uri
import android.widget.Toast

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.example.loopit.ui.screens.LoginScreen
import com.example.loopit.ui.screens.CreateAccountScreen
import com.example.loopit.ui.screens.ForgotPasswordScreen
import com.example.loopit.ui.screens.HomeRoute
import com.example.loopit.ui.screens.ProfileRoute
import com.example.loopit.ui.screens.EditProfileScreen
import com.example.loopit.ui.screens.YourImpactRoute
import com.example.loopit.ui.screens.IdentifyDeviceScreen
import com.example.loopit.ui.screens.DisposalGuidanceScreen
import com.example.loopit.ui.screens.PreDisposalChecklistScreen
import com.example.loopit.ui.screens.NotificationsScreen
import com.example.loopit.ui.screens.AccountSettingsScreen
import com.example.loopit.ui.screens.CollectionMapScreen
import com.example.loopit.ui.screens.CollectionPointDetailsScreen
import com.example.loopit.ui.screens.RewardsRoute

// Miad's recycling screens
import com.example.loopit.ui.screens.RecordRecyclingScreen
import com.example.loopit.ui.screens.RecyclingConfirmationScreen
import com.example.loopit.ui.screens.RecyclingConfirmationUiState
import com.example.loopit.ui.screens.RecyclingHistoryScreen

import com.example.loopit.ui.viewmodel.CollectionPointViewModel
import com.example.loopit.ui.viewmodel.CollectionPointViewModelFactory

@Composable
fun AppNavGraph() {

    val navController = rememberNavController()
    val context = LocalContext.current

    val collectionFactory = remember(context) {
        CollectionPointViewModelFactory(context)
    }

    val collectionViewModel: CollectionPointViewModel = viewModel(
        factory = collectionFactory
    )

    // Bottom navigation
    fun openTab(route: String) {
        navController.navigate(route) {
            launchSingleTop = true
            restoreState = true

            popUpTo(Routes.HOME) {
                saveState = true
            }
        }
    }

    // Return to Home without restoring old screens
    fun returnToHome() {
        navController.navigate(Routes.HOME) {
            popUpTo(Routes.HOME) {
                inclusive = false
                saveState = false
            }
            launchSingleTop = true
            restoreState = false
        }
    }

    fun logout() {
        navController.navigate(Routes.LOGIN) {
            popUpTo(Routes.LOGIN) {
                inclusive = false
            }
            launchSingleTop = true
        }
    }

    fun pending(message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }

    NavHost(
        navController = navController,
        startDestination = Routes.LOGIN
    ) {

        // 1. Login
        composable(Routes.LOGIN) {
            LoginScreen(
                onLoginClick = { _, _ ->
                    // Demo login
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
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
                    // Demo account creation
                    navController.navigate(Routes.HOME) {
                        popUpTo(Routes.LOGIN) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
            )
        }

        // 3. Reset Password
        composable(Routes.FORGOT_PASSWORD) {
            ForgotPasswordScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onSendResetLink = { _ ->
                    pending(
                        "Demo only: Password reset is not available yet. No email was sent."
                    )
                }
            )
        }

        // 4. Home - Reem
        composable(Routes.HOME) {
            HomeRoute(
                onNotificationsClick = {
                    navController.navigate(Routes.NOTIFICATIONS)
                },
                onIdentifyDeviceClick = {
                    navController.navigate(Routes.IDENTIFY_DEVICE)
                },
                onFindCollectionPointsClick = {
                    openTab(Routes.COLLECTION_POINTS)
                },
                onViewStatisticsClick = {
                    navController.navigate(Routes.YOUR_IMPACT)
                },
                onEnvironmentalImpactClick = {
                    navController.navigate(Routes.YOUR_IMPACT)
                },
                onHomeTabClick = {},
                onMapTabClick = {
                    openTab(Routes.COLLECTION_POINTS)
                },
                onRewardsTabClick = {
                    openTab(Routes.REWARDS)
                },
                onProfileTabClick = {
                    openTab(Routes.PROFILE)
                }
            )
        }

        // 5. Profile - Reem
        composable(Routes.PROFILE) {
            ProfileRoute(
                onHomeTabClick = {
                    openTab(Routes.HOME)
                },
                onMapTabClick = {
                    openTab(Routes.COLLECTION_POINTS)
                },
                onRewardsTabClick = {
                    openTab(Routes.REWARDS)
                },
                onRecyclingHistoryClick = {
                    navController.navigate(
                        Routes.RECYCLING_HISTORY
                    )
                },
                onYourImpactClick = {
                    navController.navigate(Routes.YOUR_IMPACT)
                },
                onRewardsClick = {
                    openTab(Routes.REWARDS)
                },
                onEditProfileClick = {
                    navController.navigate(Routes.EDIT_PROFILE)
                },
                onNotificationsClick = {
                    navController.navigate(Routes.NOTIFICATIONS)
                },
                onAccountSettingsClick = {
                    navController.navigate(Routes.ACCOUNT_SETTINGS)
                },
                onConfirmLogoutClick = {
                    logout()
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
                    // Profile saving not implemented yet
                    navController.popBackStack()
                },
                onCancelClick = {
                    navController.popBackStack()
                }
            )
        }

        // 7. Statistics - Reem
        composable(Routes.YOUR_IMPACT) {
            YourImpactRoute(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // 8. Identify Device - Qunoot
        composable(Routes.IDENTIFY_DEVICE) {
            IdentifyDeviceScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onContinueToDisposalGuidance = { deviceType ->
                    navController.navigate(
                        "${Routes.DISPOSAL_GUIDANCE}/${Uri.encode(deviceType)}"
                    )
                }
            )
        }

        // 9. Notifications - Qunoot
        composable(Routes.NOTIFICATIONS) {
            NotificationsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        // 10. Account Settings - Qunoot
        composable(Routes.ACCOUNT_SETTINGS) {
            AccountSettingsScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onEditProfileClick = {
                    navController.navigate(Routes.EDIT_PROFILE)
                },
                onChangePasswordClick = {
                    navController.navigate(Routes.FORGOT_PASSWORD)
                },
                onNotificationsClick = {
                    navController.navigate(Routes.NOTIFICATIONS)
                },
                onLogoutClick = {
                    logout()
                }
            )
        }

        // 11. Collection Points Map - Salam
        composable(Routes.COLLECTION_POINTS) {
            CollectionMapScreen(
                viewModel = collectionViewModel,
                onPointClick = { pointId ->
                    navController.navigate(
                        "${Routes.COLLECTION_POINT_DETAILS}/$pointId"
                    )
                },
                onHomeClick = {
                    openTab(Routes.HOME)
                },
                onRewardsClick = {
                    openTab(Routes.REWARDS)
                },
                onProfileClick = {
                    openTab(Routes.PROFILE)
                }
            )
        }

        // 12. Collection Point Details - Salam
        composable(
            "${Routes.COLLECTION_POINT_DETAILS}/{pointId}"
        ) { backStackEntry ->

            val pointId = backStackEntry.arguments
                ?.getString("pointId")
                ?.toIntOrNull()

            if (pointId != null) {
                CollectionPointDetailsScreen(
                    collectionPointId = pointId,
                    viewModel = collectionViewModel,
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onRecycleClick = { _, _ ->
                        navController.navigate(
                            Routes.RECORD_RECYCLING
                        )
                    },
                    onDirectionsClick = { _, _ ->
                        pending(
                            "Directions are unavailable for demo locations"
                        )
                    }
                )
            } else {
                androidx.compose.material3.Text(
                    text = "Invalid collection point"
                )
            }
        }

        // 13. Disposal Guidance - Dalia
        composable(
            "${Routes.DISPOSAL_GUIDANCE}/{deviceType}"
        ) { backStackEntry ->

            val deviceType = Uri.decode(
                backStackEntry.arguments
                    ?.getString("deviceType")
                    .orEmpty()
            )

            DisposalGuidanceScreen(
                deviceName = deviceType,
                onBackClick = {
                    navController.popBackStack()
                },
                onStartChecklistClick = {
                    navController.navigate(
                        Routes.PRE_DISPOSAL_CHECKLIST
                    )
                }
            )
        }

        // 14. Pre-Disposal Checklist - Dalia
        composable(Routes.PRE_DISPOSAL_CHECKLIST) {
            PreDisposalChecklistScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onContinueClick = {
                    navController.navigate(
                        Routes.COLLECTION_POINTS
                    )
                }
            )
        }

        // 15. Rewards
        composable(Routes.REWARDS) {
            RewardsRoute(
                onHomeTabClick = {
                    returnToHome()
                },
                onMapTabClick = {
                    openTab(Routes.COLLECTION_POINTS)
                },
                onRewardsTabClick = {},
                onProfileTabClick = {
                    openTab(Routes.PROFILE)
                }
            )
        }

        // 16. Record Recycling - Miad
        composable(Routes.RECORD_RECYCLING) {
            RecordRecyclingScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onConfirmRecyclingClick = {
                        deviceName, _, points ->

                    // Navigation only
                    // Room saving will be connected later

                    val encodedDevice = Uri.encode(deviceName)

                    navController.navigate(
                        "${Routes.RECYCLING_CONFIRMATION}/$encodedDevice/$points"
                    ) {
                        launchSingleTop = true
                    }
                }
            )
        }

        // 17. Recycling Confirmation - Miad
        composable(
            "${Routes.RECYCLING_CONFIRMATION}/{deviceName}/{points}"
        ) { backStackEntry ->

            val deviceName = Uri.decode(
                backStackEntry.arguments
                    ?.getString("deviceName")
                    .orEmpty()
            )

            val points = backStackEntry.arguments
                ?.getString("points")
                ?.toIntOrNull() ?: 0

            RecyclingConfirmationScreen(
                uiState = RecyclingConfirmationUiState(
                    deviceName = deviceName,
                    earnedPoints = points
                ),
                onViewHistoryClick = {
                    navController.navigate(
                        Routes.RECYCLING_HISTORY
                    )
                },
                onViewRewardsClick = {
                    openTab(Routes.REWARDS)
                },
                onBackToHomeClick = {
                    // Clear the recycling flow
                    returnToHome()
                }
            )
        }

        // 18. Recycling History - Miad
        composable(Routes.RECYCLING_HISTORY) {
            RecyclingHistoryScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onItemClick = {
                    pending(
                        "Record details are not available yet"
                    )
                }
            )
        }
    }
}
