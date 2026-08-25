package com.example.plaintext.ui.screens.preferences

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.screens.util.PreferenceInput
import com.example.plaintext.ui.screens.util.PreferenceItem
import com.example.plaintext.ui.viewmodel.PreferencesState
import com.example.plaintext.ui.viewmodel.PreferencesViewModel

@Composable
fun SettingsScreen(navController: NavHostController?,
                   viewModel: PreferencesViewModel = hiltViewModel()
){
    SettingsScreenContent(
        state = viewModel.preferencesState,
        onLoginChange = viewModel::updateLogin,
        onPasswordChange = viewModel::updatePassword,
        onPreencherChange = viewModel::updatePreencher
    )
}

@Composable
fun SettingsScreenContent(
    state: PreferencesState,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPreencherChange: (Boolean) -> Unit
) {
    Scaffold(
        topBar = {
            TopBarComponent()
        }
    ){ padding ->
        SettingsContent(
            modifier = Modifier.padding(padding),
            state = state,
            onLoginChange = onLoginChange,
            onPasswordChange = onPasswordChange,
            onPreencherChange = onPreencherChange
        )
    }
}

@Composable
fun SettingsContent(
    modifier: Modifier = Modifier,
    state: PreferencesState,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPreencherChange: (Boolean) -> Unit
) {
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
            fieldValue = state.login,
            summary = "Preencher login na tela inicial"
        ){
            onLoginChange(it)
        }

        // Campo para a Senha
        PreferenceInput(
            title = "Setar Senha",
            label = "Label",
            fieldValue = state.password,
            summary = "Senha para entrar no sistema"
        ){
            onPasswordChange(it)
        }

        // Switch para "Preencher Login"
        PreferenceItem(
            title = "Preencher Login",
            summary = "Preencher login na tela inicial",
            onClick = {
                onPreencherChange(!state.preencher)
            },
            control = {
                Switch(
                    checked = state.preencher,
                    onCheckedChange = {
                        onPreencherChange(it)
                    }
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreenContent(
        state = PreferencesState(login = "devtitans", password = "123", preencher = true),
        onLoginChange = {},
        onPasswordChange = {},
        onPreencherChange = {}
    )
}
