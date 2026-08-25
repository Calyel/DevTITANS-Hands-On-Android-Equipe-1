package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

data class LoginViewState(
    val login: String = "",
    val password: String = "",
    val preencher: Boolean = false,
    val loginError: Boolean = false,
    val passwordError: Boolean = false,
)

@HiltViewModel
class LoginViewModel @Inject constructor(
    handle: SavedStateHandle,
) : ViewModel() {

    var loginViewState by mutableStateOf(LoginViewState())
        private set

    fun updateLogin(login: String) {
        loginViewState = loginViewState.copy(
            login = login,
            loginError = false,
            passwordError = false,
        )
    }

    fun updatePassword(password: String) {
        loginViewState = loginViewState.copy(
            password = password,
            loginError = false,
            passwordError = false,
        )
    }

    fun updatePreencher(preencher: Boolean) {
        loginViewState = loginViewState.copy(preencher = preencher)
    }

    fun applyPreferences(preferences: PreferencesState) {
        loginViewState = loginViewState.copy(
            login = if (preferences.preencher) preferences.login else loginViewState.login,
            password = if (preferences.preencher) preferences.password else loginViewState.password,
            preencher = preferences.preencher,
        )
    }

    fun fillCredentialsFromPreferences(preferences: PreferencesState) {
        loginViewState = loginViewState.copy(
            login = preferences.login,
            password = preferences.password,
        )
    }

    fun submit(expectedLogin: String, expectedPassword: String): Boolean {
        val isValid = loginViewState.login == expectedLogin &&
            loginViewState.password == expectedPassword

        if (!isValid) {
            loginViewState = loginViewState.copy(
                loginError = true,
                passwordError = true,
            )
        }

        return isValid
    }
}
