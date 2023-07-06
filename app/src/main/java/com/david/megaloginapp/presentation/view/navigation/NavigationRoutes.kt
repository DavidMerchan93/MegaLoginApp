package com.david.megaloginapp.presentation.view.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class NavigationRoutes(
    val baseRoute: String,
    val navArgs: List<NavigationArgs> = emptyList(),
) {

    val route: String = run {
        val argsKey = navArgs.map { "{${it.key}}" }
        listOf(baseRoute).plus(argsKey).joinToString("/")
    }

    val args = navArgs.map {
        navArgument(it.key) {
            type = it.type
        }
    }

    object Login : NavigationRoutes(baseRoute = LOGIN_PATH)
    object Register : NavigationRoutes(baseRoute = REGISTER_PATH)
    object ForgotPassword : NavigationRoutes(baseRoute = FORGOT_PASSWORD_PATH)
    object Home : NavigationRoutes(
        baseRoute = HOME_PATH,
        navArgs = listOf(NavigationArgs.USER_NAME),
    ) {
        fun createRoute(id: Int): String {
            return listOf(
                baseRoute,
                id,
            ).joinToString("/")
        }
    }
}

enum class NavigationArgs(val key: String, val type: NavType<*> = NavType.StringType) {
    USER_NAME("userId", NavType.IntType),
}

private const val LOGIN_PATH = "login"
private const val REGISTER_PATH = "register"
private const val FORGOT_PASSWORD_PATH = "forgotPassword"
private const val HOME_PATH = "home"
