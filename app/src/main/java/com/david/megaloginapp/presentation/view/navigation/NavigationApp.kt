package com.david.megaloginapp.presentation.view.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.david.megaloginapp.presentation.view.screen.ForgotPasswordScreen
import com.david.megaloginapp.presentation.view.screen.HomeScreen
import com.david.megaloginapp.presentation.view.screen.LoginScreen
import com.david.megaloginapp.presentation.view.screen.RegisterScreen

@Composable
fun NavigationApp(navHostController: NavHostController) {
    NavHost(navController = navHostController, startDestination = NavigationRoutes.Login.route) {
        loginNavigation(navHostController)
        registerNavigation(navHostController)
        forgotPasswordNavigation(navHostController)
        homeNavigation(navHostController)
    }
}

private fun NavGraphBuilder.loginNavigation(navController: NavController) {
    composable(NavigationRoutes.Login) {
        LoginScreen(
            onLogin = { userName ->
                navController.navigate(NavigationRoutes.Home.createRoute(userName))
            },
            onForgotPassword = {
                navController.navigate(NavigationRoutes.ForgotPassword.baseRoute)
            },
            onRegister = {
                navController.navigate(NavigationRoutes.Register.baseRoute)
            },
        )
    }
}

private fun NavGraphBuilder.registerNavigation(navController: NavController) {
    composable(NavigationRoutes.Register) {
        RegisterScreen(
            onContinueToHome = { userId ->
                navController.navigate(NavigationRoutes.Home.createRoute(userId))
            },
        )
    }
}

private fun NavGraphBuilder.forgotPasswordNavigation(navController: NavController) {
    composable(NavigationRoutes.ForgotPassword) {
        ForgotPasswordScreen()
    }
}

private fun NavGraphBuilder.homeNavigation(navController: NavController) {
    composable(NavigationRoutes.Home) {
        HomeScreen()
    }
}

private fun NavGraphBuilder.composable(
    navRoute: NavigationRoutes,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(navRoute.route, navRoute.args) {
        content(it)
    }
}
