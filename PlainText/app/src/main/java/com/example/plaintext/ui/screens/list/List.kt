package com.example.plaintext.ui.screens.list

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.material3.Scaffold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.plaintext.R
import com.example.plaintext.data.model.PasswordInfo
import com.example.plaintext.ui.screens.login.TopBarComponent
import com.example.plaintext.ui.theme.PlainTextTheme
import com.example.plaintext.ui.viewmodel.ListViewModel
import com.example.plaintext.ui.viewmodel.ListViewState


@Composable
fun ListView(
    navigateToEdit: (password: PasswordInfo) -> Unit,
    navigateToSettings: () -> Unit,
    navigateToSensors: () -> Unit,
    navigateToLogin: () -> Unit,
    viewModel: ListViewModel = hiltViewModel()
) {
    val listState = viewModel.listViewState

    Scaffold(
        topBar = {
            TopBarComponent(
                navigateToSettings = navigateToSettings,
                navigateToSensores = navigateToSensors,
                navigateToLogin = navigateToLogin
            )
        },
        floatingActionButton = {
            AddButton(onClick = { navigateToEdit(PasswordInfo()) })
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->
        ListItemContent(
            modifier = Modifier.padding(innerPadding),
            listState = listState,
            navigateToEdit = navigateToEdit
        )
    }
}

@Composable
fun AddButton(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.secondary
    ) {
        Icon(Icons.Filled.Add, "Small floating action button.")
    }
}
 
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListItemContent(
    modifier: Modifier,
    listState: ListViewState,
    navigateToEdit: (password: PasswordInfo) -> Unit
) {
        when {
            !listState.isCollected -> {
                LoadingScreen()
            }
 
            else -> {
                LazyColumn(
                    modifier = modifier
                        .fillMaxSize()
                ) {
                    items(listState.passwordList.size) {
                        ListItem(
                            listState.passwordList[it],
                            navigateToEdit
                        )
                    }
                }
            }
        }
}
 
@Composable
fun LoadingScreen() {
    Row(
        modifier = Modifier.fillMaxSize(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Text("Carregando")
    }
}
 
@Composable
fun ListItem(
    password: PasswordInfo,
    navigateToEdit: (password: PasswordInfo) -> Unit
) {
    val title = password.name
    val subTitle = password.login
 
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(70.dp)
            .clickable { navigateToEdit(password) }
            .padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Logo",
            modifier = Modifier.fillMaxHeight()
        )
        Column(
            modifier = Modifier
                .weight(.7f)
                .padding(horizontal = 5.dp),
        ) {
            Text(title, fontSize = 20.sp)
            Text(subTitle, fontSize = 14.sp)
        }
        Icon(
            Icons.AutoMirrored.Filled.KeyboardArrowRight, contentDescription = "Menu",
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ListViewPreview() {
    PlainTextTheme {
        val sampleList = listOf(
            PasswordInfo(name = "Twitter", login = "dev"),
            PasswordInfo(name = "Facebook", login = "devtitans"),
            PasswordInfo(name = "Moodle", login = "dev.com")
        )
        val listState = ListViewState(passwordList = sampleList, isCollected = true)

        Scaffold(
            topBar = {
                TopBarComponent(
                    navigateToSettings = {},
                    navigateToSensores = {}
                )
            },
            floatingActionButton = {
                AddButton(onClick = {})
            },
            containerColor = MaterialTheme.colorScheme.background
        ) { innerPadding ->
            ListItemContent(
                modifier = Modifier.padding(innerPadding),
                listState = listState,
                navigateToEdit = {}
            )
        }
    }
}

