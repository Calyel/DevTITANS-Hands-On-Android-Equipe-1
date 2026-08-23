package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// Estado da tela de preferências
data class PreferencesState(
    var login: String = "devtitans",     // Valor padrão
    var password: String = "123",        // Valor padrão
    var preencher: Boolean = true        // Valor padrão (switch ligado)
)

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    handle: SavedStateHandle,
) : ViewModel() {

    // Estado observável pela UI
    var preferencesState by mutableStateOf(PreferencesState())
        private set

    // Função para atualizar o login
    fun updateLogin(login: String) {
        preferencesState = preferencesState.copy(login = login)
        // Aqui você pode adicionar lógica para salvar no DataStore/Preferences
    }

    // Função para atualizar a senha
    fun updatePassword(password: String) {
        preferencesState = preferencesState.copy(password = password)
        // Aqui você pode adicionar lógica para salvar no DataStore/Preferences
    }

    // Função para atualizar o estado do switch
    fun updatePreencher(preencher: Boolean) {
        preferencesState = preferencesState.copy(preencher = preencher)
        // Aqui você pode adicionar lógica para salvar no DataStore/Preferences
    }

    // Função para validar credenciais (usada na tela de login)
    fun checkCredentials(login: String, password: String): Boolean {
        return login == preferencesState.login && password == preferencesState.password
    }
}