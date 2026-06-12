package com.example.plaintext.ui.screens.login

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.plaintext.R
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import com.example.plaintext.data.UserSession
import com.example.plaintext.ui.viewmodel.LoginViewModel

data class LoginState(
    val preencher: Boolean,
    val login: String,
    val navigateToSettings: () -> Unit,
    val navigateToList: (name: String) -> Unit,
    val checkCredentials: (login: String, password: String) -> Boolean,
)

@Composable
fun Login_screen(
    navigateToSettings: () -> Unit,
    navigateToList: () -> Unit,
    navigateToRegister: () -> Unit,
    onExit: () -> Unit = {},
    preferencesViewModel: PreferencesViewModel = hiltViewModel(),
    loginViewModel: LoginViewModel = hiltViewModel()
) {
    val uiState = loginViewModel.uiState
    val preferencesState = preferencesViewModel.preferencesState
    val context = LocalContext.current

    // Pré-preenchimento automático se configurado nas preferências
    LaunchedEffect(preferencesState.preencher) {
        if (preferencesState.preencher && uiState.username.isEmpty()) {
            loginViewModel.onUsernameChange(preferencesState.login)
            loginViewModel.onPasswordChange(preferencesState.password)
        }
    }

    Scaffold(
        topBar = {
            TopBarComponent(
                navigateToSettings = navigateToSettings,
                onExit = onExit
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(padding)
                .padding(24.dp)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Password Manager",
                fontSize = 28.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "Faça login para acessar suas senhas"
            )

            Spacer(modifier = Modifier.height(40.dp))

            OutlinedTextField(
                value = uiState.username,
                onValueChange = { loginViewModel.onUsernameChange(it) },
                label = { Text("Usuário") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            )

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = uiState.password,
                onValueChange = { loginViewModel.onPasswordChange(it) },
                label = { Text("Senha") },
                visualTransformation = PasswordVisualTransformation(),
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    loginViewModel.login(
                        expectedUser = preferencesState.login,
                        expectedPass = preferencesState.password,
                        onSuccess = {
                            navigateToList()
                        },
                        onError = {
                            Toast.makeText(
                                context,
                                "Usuário ou senha inválidos",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    )
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                if (uiState.isLoading) {
                    Text("Entrando...")
                } else {
                    Text("Entrar")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Button(
                onClick = {
                    navigateToRegister()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            ) {
                Text("Criar Conta")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun Login_screenPreview() {
    Login_screen(
        navigateToSettings = {},
        navigateToList = {},
        navigateToRegister = {}
    )
}

@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },

            title = { Text(text = "Sobre") },
            text = { Text(text = "PlainText Password Manager v1.0") },
            confirmButton = {
                Button(
                    onClick = { shouldShowDialog.value = false }
                ) {
                    Text(text = "Ok")
                }
            }
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarComponent(
    navigateToSettings: (() -> Unit)? = null,
    navigateToSensores: (() -> Unit)? = null,
    navigateBack: (() -> Unit)? = null,
    onLogout: (() -> Unit)? = null,
    onExit: (() -> Unit)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text("PlainText") },
        navigationIcon = {
            if (navigateBack != null) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Voltar"
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Menu"
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                if (navigateToSettings != null) {
                    DropdownMenuItem(
                        text = { Text("Configurações") },
                        onClick = {
                            expanded = false
                            navigateToSettings()
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }

                if (onLogout != null) {
                    DropdownMenuItem(
                        text = { Text("Logout") },
                        onClick = {
                            expanded = false
                            onLogout()
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }

                DropdownMenuItem(
                    text = { Text("Sobre") },
                    onClick = {
                        expanded = false
                        showAboutDialog = true
                    },
                    modifier = Modifier.padding(8.dp)
                )

                if (onExit != null) {
                    DropdownMenuItem(
                        text = { Text("Sair") },
                        onClick = {
                            expanded = false
                            onExit()
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    )

    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = {
                showAboutDialog = false
            },
            title = {
                Text(text = "Sobre")
            },
            text = {
                Text(
                    text = "PlainText Password Manager v1.0\n\nAplicativo para gerenciamento local de senhas."
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        showAboutDialog = false
                    }
                ) {
                    Text(text = "OK")
                }
            }
        )
    }
}