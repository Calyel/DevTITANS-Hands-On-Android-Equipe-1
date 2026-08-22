package com.example.plaintext.ui.screens.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plaintext.R
import com.example.plaintext.ui.theme.GreenBanner
import com.example.plaintext.ui.viewmodel.PreferencesViewModel
import com.example.plaintext.ui.viewmodel.LoginState
import androidx.compose.ui.tooling.preview.Preview
import com.example.plaintext.ui.theme.PlainTextTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Login_screen(
    navigateToSettings: () -> Unit,
    navigateToList: (name: String) -> Unit,
    viewModel: PreferencesViewModel = hiltViewModel()
) {
    val loginState = viewModel.toLoginState(navigateToSettings, navigateToList)
    LoginScreenContent(loginState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreenContent(
    loginState: LoginState
) {
    var login by remember { mutableStateOf(if (loginState.preencher) loginState.login else "") }
    var password by remember { mutableStateOf("") }
    var saveLogin by remember { mutableStateOf(loginState.preencher) }
    var showError by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBarComponent(
                navigateToSettings = loginState.navigateToSettings,
                navigateToSensores = { /* No sensors for now */ }
            )
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(MaterialTheme.colorScheme.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(GreenBanner)
                    .padding(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier.size(100.dp)
                )
                Column(modifier = Modifier.padding(start = 16.dp)) {
                    Text(
                        text = stringResource(R.string.banner_title),
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = stringResource(R.string.banner_subtitle),
                        color = Color.White,
                        fontSize = 16.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            Text(
                text = stringResource(R.string.title_login),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.login),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.width(80.dp)
                )
                OutlinedTextField(
                    value = login,
                    onValueChange = { login = it },
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Gray
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.password),
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.width(80.dp)
                )
                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = Color.Gray
                    )
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Checkbox(
                    checked = saveLogin,
                    onCheckedChange = { saveLogin = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = MaterialTheme.colorScheme.primary,
                        uncheckedColor = Color.Gray
                    )
                )
                Text(
                    text = stringResource(R.string.save_information_login_text),
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    if (loginState.checkCredentials(login, password)) {
                        loginState.navigateToList(login)
                    } else {
                        showError = true
                    }
                },
                modifier = Modifier
                    .width(150.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                shape = RoundedCornerShape(25.dp)
            ) {
                Text(
                    text = stringResource(R.string.text_button_send),
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            if (showError) {
                Card(
                    modifier = Modifier
                        .padding(bottom = 32.dp)
                        .wrapContentSize(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = Color(0xFF1A1A1A)
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Surface(
                            modifier = Modifier.size(32.dp),
                            shape = CircleShape,
                            color = Color(0xFF3DDC84)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_launcher_foreground),
                                contentDescription = null,
                                modifier = Modifier.padding(4.dp),
                                tint = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Text(
                            text = stringResource(R.string.invalid_credentials),
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }

                LaunchedEffect(showError) {
                    kotlinx.coroutines.delay(3000)
                    showError = false
                }
            }
        }
    }
}

@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = {
                shouldShowDialog.value = false
            },
            text = { Text(text = "PlainText Password Manager v1.0") },
            confirmButton = {
                TextButton(onClick = { shouldShowDialog.value = false }) {
                    Text("OK")
                }
            },
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarComponent(
    navigateToSettings: (() -> Unit?)? = null,
    navigateToSensores: (() -> Unit?)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    val shouldShowDialog = remember { mutableStateOf(false) }

    if (shouldShowDialog.value) {
        MyAlertDialog(shouldShowDialog = shouldShowDialog)
    }

    TopAppBar(
        title = { Text("PlainText", color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface,
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
        actions = {
            if (navigateToSettings != null && navigateToSensores != null) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu")
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    DropdownMenuItem(
                        text = { Text("Configurações") },
                        onClick = {
                            navigateToSettings()
                            expanded = false
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                    DropdownMenuItem(
                        text = {
                            Text("Sobre")
                        },
                        onClick = {
                            shouldShowDialog.value = true;
                            expanded = false
                        },
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    PlainTextTheme {
        LoginScreenContent(
            loginState = LoginState(
                preencher = true,
                login = "devtitans",
                navigateToSettings = {},
                navigateToList = {},
                checkCredentials = { _, _ -> true }
            )
        )
    }
}
