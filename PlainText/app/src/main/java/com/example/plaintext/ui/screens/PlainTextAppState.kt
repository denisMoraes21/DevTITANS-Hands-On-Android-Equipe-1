package com.example.plaintext.ui.screens

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.plaintext.data.model.PasswordInfo
import kotlinx.serialization.Serializable

@Serializable
sealed class Screen() {

    @Serializable
    object Login;

    @Serializable
    object Register

    @Serializable
    object List;

    @Serializable
    object Preferences;

    @Serializable
    data class EditList(
        val password: PasswordInfo,
        val title: String
    );
}

@Composable
fun rememberPlainTextAppState(
    navController: NavHostController = rememberNavController(),
    context: Context = LocalContext.current
) = remember(navController, context) {
    PlainTextAppState(navController, context)
}


class PlainTextAppState(
    val navController: NavHostController,
    private val context: Context
) {

    fun checkRoute(route: String): Boolean {
        val currentRoute = navController.currentBackStackEntry?.destination?.route.toString()

        return currentRoute != route
    }

    fun navigateToLogin(){
        navController.navigate(Screen.Login)
    }

    fun navigateToList() {
        navController.navigate(Screen.List)
    }

    fun navigateToPreferences() {
        navController.navigate(Screen.Preferences)
    }

    fun logout() {
        navController.navigate(Screen.Login) {
            popUpTo(0) { inclusive = true }
        }
    }

    fun navigateToEditList(password: PasswordInfo, title: String) {
        navController.navigate(Screen.EditList(password, title))
    }

}

/**
 * If the lifecycle is not resumed it means this NavBackStackEntry already processed a nav event.
 *
 * This is used to de-duplicate navigation events.
 */
private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED
