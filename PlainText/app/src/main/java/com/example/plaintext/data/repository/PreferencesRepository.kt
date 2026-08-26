package com.example.plaintext.data.repository

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.plaintext.ui.viewmodel.PreferencesState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesRepository @Inject constructor() {
    var preferencesState by mutableStateOf(PreferencesState())
        private set

    fun updateLogin(login: String) {
        preferencesState = preferencesState.copy(login = login)
    }

    fun updatePassword(password: String) {
        preferencesState = preferencesState.copy(password = password)
    }

    fun updatePreencher(preencher: Boolean) {
        preferencesState = preferencesState.copy(preencher = preencher)
    }
}
