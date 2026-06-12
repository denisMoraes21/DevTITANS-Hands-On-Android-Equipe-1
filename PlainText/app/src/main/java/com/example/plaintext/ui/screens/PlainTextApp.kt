package com.example.plaintext.ui.screens

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.toRoute
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.editList.EditList
import com.example.plaintext.ui.screens.list.AddButton
import com.example.plaintext.ui.screens.list.ListView
import com.example.plaintext.ui.screens.login.Login_screen
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.screens.preferences.SettingsScreen
import com.example.plaintext.ui.viewmodel.ListViewModel
import com.example.plaintext.utils.parcelableType
import kotlin.reflect.typeOf
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import android.app.Activity
import com.example.plaintext.ui.screens.register.RegisterScreen

@Composable
fun PlainTextApp(
    appState: PlainTextAppState = rememberPlainTextAppState()
) {
    val context = LocalContext.current
    val onExit = { (context as? Activity)?.finish() }

    NavHost(
        navController = appState.navController,
        startDestination = Screen.Login,
    )
    {
        composable<Screen.Login>{
            Login_screen(
                navigateToSettings = { 
                    appState.navigateToPreferences()
                    Unit
                },
                navigateToList = { 
                    appState.navigateToList()
                    Unit
                },
                navigateToRegister = {
                    appState.navController.navigate(Screen.Register)
                },
                onExit = { 
                    onExit()
                    Unit
                }
            )
        }

        composable<Screen.Preferences> {
            SettingsScreen(
                navController = appState.navController,
                onLogout = { 
                    appState.logout()
                    Unit
                },
                onExit = { 
                    onExit()
                    Unit
                }
            )
        }
        composable<Screen.Register> {
            RegisterScreen(
                navigateBack = {
                    appState.navController.popBackStack()
                }
            )
        }
        composable<Screen.List> {
            val viewModel: ListViewModel = hiltViewModel()
            ListView(
                viewModel = viewModel,
                navigateToEdit = { password ->
                    val title = if (password.id == 0) "Adicionar nova senha" else "Editar Senha"
                    appState.navigateToEditList(password, title)
                },
                navigateToSettings = { 
                    appState.navigateToPreferences()
                    Unit
                },
                onLogout = { 
                    appState.logout()
                    Unit
                },
                onExit = { 
                    onExit()
                    Unit
                }
            )
        }

        composable<Screen.EditList>(
            typeMap = mapOf(typeOf<PasswordInfo>() to parcelableType<PasswordInfo>())
        ) { it ->
            val args = it.toRoute<Screen.EditList>()
            val viewModel: ListViewModel = hiltViewModel()
            EditList(
                args = args,
                navigateBack = { appState.navController.popBackStack() },
                savePassword = { viewModel.savePassword(it) },
                deletePassword = { viewModel.deletePassword(it) }
            )
        }
    }
}
