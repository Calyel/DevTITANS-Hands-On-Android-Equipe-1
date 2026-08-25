package com.example.plaintext.ui.screens.editList

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.Screen
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.theme.GreenBanner
import com.example.plaintext.ui.theme.PlainTextTheme

data class EditListState(
    val nomeState: MutableState<String>,
    val usuarioState: MutableState<String>,
    val senhaState: MutableState<String>,
    val notasState: MutableState<String>,
)

fun isPasswordEmpty(password: PasswordInfo): Boolean {
    return password.id == 0 && password.name.isEmpty() && password.login.isEmpty() && password.password.isEmpty() && password.notes?.isEmpty() == true
}

@Composable
fun EditList(
    args: Screen.EditList,
    navigateBack: () -> Unit,
    savePassword: (password: PasswordInfo) -> Unit
) {
    val state = remember {
        EditListState(
            nomeState = mutableStateOf(args.password.name),
            usuarioState = mutableStateOf(args.password.login),
            senhaState = mutableStateOf(args.password.password),
            notasState = mutableStateOf(args.password.notes ?: "")
        )
    }

    val isNew = isPasswordEmpty(args.password)

    Scaffold(
        topBar = {
            TopBarComponent()
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(GreenBanner)
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = if (isNew) "Adicionar nova senha" else "Editar senha",
                    color = Color.White,
                    fontSize = 20.sp
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            EditInput(textInputLabel = "Nome", textInputState = state.nomeState)
            EditInput(textInputLabel = "Usuário", textInputState = state.usuarioState)
            EditInput(textInputLabel = "Senha", textInputState = state.senhaState)
            EditInput(textInputLabel = "Notas", textInputState = state.notasState, textInputHeight = 150)

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    val updatedPassword = args.password.copy(
                        name = state.nomeState.value,
                        login = state.usuarioState.value,
                        password = state.senhaState.value,
                        notes = state.notasState.value
                    )
                    savePassword(updatedPassword)
                    navigateBack()
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.secondaryContainer
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = "Salvar",
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}


@Composable
fun EditInput(
    textInputLabel: String,
    textInputState: MutableState<String> = mutableStateOf(""),
    textInputHeight: Int = 60
) {
    val padding: Int = 30

    var textState by rememberSaveable { textInputState }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(textInputHeight.dp)
            .padding(horizontal = padding.dp),
        horizontalArrangement = Arrangement.Center,
    ) {
        OutlinedTextField(
            value = textState,
            onValueChange = { textState = it },
            label = { Text(textInputLabel) },
            modifier = Modifier
                .height(textInputHeight.dp)
                .fillMaxWidth()
        )

    }
    Spacer(modifier = Modifier.height(10.dp))
}

@Preview(showBackground = true)
@Composable
fun EditListPreview() {
    PlainTextTheme {
        EditList(
            Screen.EditList(PasswordInfo(1, "Facebook", "devtitans", "Senha", "Notas")),
            navigateBack = {},
            savePassword = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AddListPreview() {
    PlainTextTheme {
        EditList(
            Screen.EditList(PasswordInfo()),
            navigateBack = {},
            savePassword = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditInputPreview() {
    EditInput("Nome")
}