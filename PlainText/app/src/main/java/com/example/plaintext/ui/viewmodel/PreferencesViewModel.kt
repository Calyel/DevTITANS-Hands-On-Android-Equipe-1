package com.example.plaintext.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.plaintext.data.repository.PreferencesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

// Estado da tela de preferências
data class PreferencesState(
    val login: String = "devtitans",     // Valor padrão
    val password: String = "123",        // Valor padrão
    val preencher: Boolean = true        // Valor padrão (switch ligado)
)

@HiltViewModel
class PreferencesViewModel @Inject constructor(
    private val repository: PreferencesRepository,
    handle: SavedStateHandle,
) : ViewModel() {

    // Estado observável pela UI
    val preferencesState get() = repository.preferencesState

    // Função para atualizar o login
    fun updateLogin(login: String) {
        repository.updateLogin(login)
    }

    // Função para atualizar a senha
    fun updatePassword(password: String) {
        repository.updatePassword(password)
    }

    // Função para atualizar o estado do switch
    fun updatePreencher(preencher: Boolean) {
        repository.updatePreencher(preencher)
    }
}
