package com.example.plaintext.ui.screens.preferences

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.screens.util.PreferenceInput
import com.example.plaintext.ui.screens.util.PreferenceItem
import com.example.plaintext.ui.viewmodel.PreferencesViewModel

@Composable
fun SettingsScreen(
    navController: NavHostController?,
    viewModel: PreferencesViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = { TopBarComponent() }
    ) { padding ->
        SettingsContent(
            modifier = Modifier.padding(padding),
            viewModel = viewModel
        )
    }
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    viewModel: PreferencesViewModel
) {
    // Obtém o estado atual do ViewModel
    val state = viewModel.preferencesState

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Campo para o Login
        PreferenceInput(
            title = "Preencher Login",
            label = "Login",
            fieldValue = state.login, // Valor atual do estado
            summary = "Preencher login na tela inicial"
        ) { newLogin ->
            viewModel.updateLogin(newLogin) // Atualiza o ViewModel
        }

        // Campo para a Senha
        PreferenceInput(
            title = "Setar Senha",
            label = "Senha",
            fieldValue = state.password, // Valor atual do estado
            summary = "Senha para entrar no sistema"
        ) { newPassword ->
            viewModel.updatePassword(newPassword) // Atualiza o ViewModel
        }

        // Switch para "Preencher Login"
        PreferenceItem(
            title = "Preencher Login",
            summary = "Preencher login na tela inicial",
            onClick = {
                // Ação opcional ao clicar no item
            },
            control = {
                Switch(
                    checked = state.preencher, // Estado atual do switch
                    onCheckedChange = { newValue ->
                        viewModel.updatePreencher(newValue) // Atualiza o ViewModel
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(null)
}