package com.example.plaintext.ui.screens.login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plaintext.ui.theme.PlainTextTheme
import com.example.plaintext.ui.viewmodel.LoginViewModel
import com.example.plaintext.ui.viewmodel.PreferencesViewModel

private val DarkBrown = Color(0xFF1B120F)
private val WarmBrown = Color(0xFF2A1A14)
private val CardBrown = Color(0xFF352218)
private val Peach = Color(0xFFEFA87B)
private val PeachSoft = Color(0x33EFA87B)
private val TextMuted = Color(0xFFB8A89E)
private val FieldBorderGray = Color(0xFF6B5A50)
private val ErrorRed = Color(0xFFE57373)

@Composable
fun Login_screen(
    navigateToSettings: () -> Unit,
    navigateToList: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel(),
) {
    val loginState = viewModel.loginViewState
    val preferencesViewModel: PreferencesViewModel = hiltViewModel()
    val preferencesState = preferencesViewModel.preferencesState
    val context = LocalContext.current

    LaunchedEffect(preferencesState) {
        viewModel.applyPreferences(preferencesState)
    }

    LoginScreenContent(
        login = loginState.login,
        password = loginState.password,
        preencher = loginState.preencher,
        loginError = loginState.loginError,
        passwordError = loginState.passwordError,
        onLoginChange = viewModel::updateLogin,
        onPasswordChange = viewModel::updatePassword,
        onPreencherChange = { checked ->
            preferencesViewModel.updatePreencher(checked)
            viewModel.updatePreencher(checked)
            if (checked) {
                viewModel.fillCredentialsFromPreferences(preferencesState)
            }
        },
        onSubmit = {
            if (viewModel.submit(preferencesState.login, preferencesState.password)) {
                navigateToList()
            } else {
                Toast.makeText(context, "Credenciais inválidas!", Toast.LENGTH_SHORT).show()
            }
        },
        navigateToSettings = navigateToSettings,
    )
}

@Composable
private fun LoginScreenContent(
    login: String,
    password: String,
    preencher: Boolean,
    loginError: Boolean = false,
    passwordError: Boolean = false,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPreencherChange: (Boolean) -> Unit,
    onSubmit: () -> Unit,
    navigateToSettings: () -> Unit,
) {
    Scaffold(
        containerColor = DarkBrown,
        topBar = {
            TopBarComponent(navigateToSettings = navigateToSettings)
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        colors = listOf(WarmBrown, DarkBrown, Color(0xFF0F0A08)),
                    ),
                ),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 24.dp, vertical = 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LoginBranding()

                Spacer(modifier = Modifier.height(28.dp))

                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = CardBrown),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Text(
                            text = "Bem-vindo de volta",
                            color = Color.White,
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold,
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Digite suas credenciais para continuar",
                            color = TextMuted,
                            textAlign = TextAlign.Center,
                            fontSize = 14.sp,
                            lineHeight = 20.sp,
                        )

                        Spacer(modifier = Modifier.height(28.dp))

                        LoginField(
                            label = "Login",
                            value = login,
                            onValueChange = onLoginChange,
                            leadingIcon = Icons.Default.Person,
                            borderColor = if (loginError) ErrorRed else Peach,
                            isError = loginError,
                            errorMessage = if (loginError) "Login inválido" else null,
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        LoginField(
                            label = "Senha",
                            value = password,
                            onValueChange = onPasswordChange,
                            leadingIcon = Icons.Default.Lock,
                            borderColor = if (passwordError) ErrorRed else FieldBorderGray,
                            isPassword = true,
                            isError = passwordError,
                            errorMessage = if (passwordError) "Senha inválida" else null,
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Checkbox(
                                checked = preencher,
                                onCheckedChange = onPreencherChange,
                                colors = CheckboxDefaults.colors(
                                    checkedColor = Peach,
                                    uncheckedColor = TextMuted,
                                    checkmarkColor = DarkBrown,
                                ),
                            )
                            Text(
                                text = "Salvar as informações de login",
                                color = TextMuted,
                                fontSize = 14.sp,
                            )
                        }

                        Spacer(modifier = Modifier.height(24.dp))

                        Button(
                            onClick = onSubmit,
                            shape = RoundedCornerShape(50),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Peach,
                                contentColor = DarkBrown,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(52.dp),
                        ) {
                            Text(
                                text = "Enviar",
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "\"The most secure password manager\"",
                    color = TextMuted.copy(alpha = 0.7f),
                    fontSize = 12.sp,
                    textAlign = TextAlign.Center,
                    fontStyle = FontStyle.Italic,
                )
            }
        }
    }
}

@Composable
private fun LoginBranding() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(top = 8.dp),
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(CircleShape)
                .background(PeachSoft)
                .border(2.dp, Peach.copy(alpha = 0.5f), CircleShape),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                tint = Peach,
                modifier = Modifier.size(36.dp),
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "PlainText",
            color = Color.White,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Gerenciador de senhas seguro",
            color = Peach.copy(alpha = 0.85f),
            fontSize = 14.sp,
        )
    }
}

@Composable
private fun LoginField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    leadingIcon: ImageVector,
    borderColor: Color,
    isPassword: Boolean = false,
    isError: Boolean = false,
    errorMessage: String? = null,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = label,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            isError = isError,
            leadingIcon = {
                Icon(
                    imageVector = leadingIcon,
                    contentDescription = null,
                    tint = if (isError) ErrorRed else Peach.copy(alpha = 0.8f),
                )
            },
            visualTransformation = if (isPassword) {
                PasswordVisualTransformation()
            } else {
                VisualTransformation.None
            },
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = borderColor,
                unfocusedBorderColor = borderColor.copy(alpha = 0.6f),
                errorBorderColor = ErrorRed,
                cursorColor = Peach,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedContainerColor = WarmBrown.copy(alpha = 0.5f),
                unfocusedContainerColor = WarmBrown.copy(alpha = 0.3f),
                errorContainerColor = WarmBrown.copy(alpha = 0.3f),
            ),
        )
        if (errorMessage != null) {
            Text(
                text = errorMessage,
                color = ErrorRed,
                fontSize = 12.sp,
                modifier = Modifier.padding(start = 4.dp, top = 4.dp),
            )
        }
    }
}

@Composable
fun MyAlertDialog(shouldShowDialog: MutableState<Boolean>) {
    if (shouldShowDialog.value) {
        AlertDialog(
            onDismissRequest = { shouldShowDialog.value = false },
            title = { Text(text = "Sobre") },
            text = { Text(text = "PlainText Password Manager v1.0") },
            confirmButton = {
                Button(onClick = { shouldShowDialog.value = false }) {
                    Text(text = "Ok")
                }
            },
        )
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun TopBarComponent(
    navigateToSettings: (() -> Unit)? = null,
    navigateToSensores: (() -> Unit)? = null,
) {
    var expanded by remember { mutableStateOf(false) }
    val shouldShowDialog = remember { mutableStateOf(false) }

    if (shouldShowDialog.value) {
        MyAlertDialog(shouldShowDialog = shouldShowDialog)
    }

    TopAppBar(
        title = { Text("PlainText", color = Color.White) },
        colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBrown),
        actions = {
            if (navigateToSettings != null) {
                IconButton(onClick = { expanded = true }) {
                    Icon(Icons.Default.MoreVert, contentDescription = "Menu", tint = Color.White)
                }
                DropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false },
                ) {
                    DropdownMenuItem(
                        text = { Text("Configurações") },
                        onClick = {
                            navigateToSettings()
                            expanded = false
                        },
                    )
                    if (navigateToSensores != null) {
                        DropdownMenuItem(
                            text = { Text("Sensores") },
                            onClick = {
                                navigateToSensores()
                                expanded = false
                            },
                        )
                    }
                    DropdownMenuItem(
                        text = { Text("Sobre") },
                        onClick = {
                            shouldShowDialog.value = true
                            expanded = false
                        },
                    )
                }
            }
        },
    )
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    PlainTextTheme {
        LoginScreenContent(
            login = "devtitans",
            password = "",
            preencher = false,
            onLoginChange = {},
            onPasswordChange = {},
            onPreencherChange = {},
            onSubmit = {},
            navigateToSettings = {},
        )
    }
}
