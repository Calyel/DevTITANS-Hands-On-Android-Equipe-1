package com.example.plaintext.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import android.os.Parcelable
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.parcelize.Parcelize
import javax.inject.Inject

private const val LOGIN_VIEW_STATE_KEY = "login_view_state"

@Parcelize
data class LoginViewState(
    val login: String = "",
    val password: String = "",
    val preencher: Boolean = false,
    val loginError: Boolean = false,
    val passwordError: Boolean = false,
) : Parcelable

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val handle: SavedStateHandle,
) : ViewModel() {

    var loginViewState by mutableStateOf(
        handle.get<LoginViewState>(LOGIN_VIEW_STATE_KEY) ?: LoginViewState()
    )
        private set

    private fun updateState(state: LoginViewState) {
        loginViewState = state
        handle[LOGIN_VIEW_STATE_KEY] = state
    }

    fun updateLogin(login: String) {
        updateState(
            loginViewState.copy(
                login = login,
                loginError = false,
                passwordError = false,
            )
        )
    }

    fun updatePassword(password: String) {
        updateState(
            loginViewState.copy(
                password = password,
                loginError = false,
                passwordError = false,
            )
        )
    }

    fun updatePreencher(preencher: Boolean) {
        updateState(loginViewState.copy(preencher = preencher))
    }

    fun applyPreferences(preferences: PreferencesState) {
        updateState(
            loginViewState.copy(
                login = if (preferences.preencher) preferences.login else loginViewState.login,
                password = if (preferences.preencher) preferences.password else loginViewState.password,
                preencher = preferences.preencher,
            )
        )
    }

    fun fillCredentialsFromPreferences(preferences: PreferencesState) {
        updateState(
            loginViewState.copy(
                login = preferences.login,
                password = preferences.password,
            )
        )
    }

    fun submit(expectedLogin: String, expectedPassword: String): Boolean {
        val isValid = loginViewState.login == expectedLogin &&
            loginViewState.password == expectedPassword

        if (!isValid) {
            updateState(
                loginViewState.copy(
                    loginError = true,
                    passwordError = true,
                )
            )
        }

        return isValid
    }
}
