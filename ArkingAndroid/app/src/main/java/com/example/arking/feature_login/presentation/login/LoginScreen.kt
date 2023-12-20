package com.example.arking.feature_login.presentation.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.arking.feature_otis.presentation.add_edit_oti.AddEditOtiViewModel

@Composable
fun LoginScreen(
    onLoginClick: () -> Unit = {},
    viewModel: LoginViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val onEvent = viewModel::onEvent
    val context = LocalContext.current
    /*val isEmailValid by remember(username) {
        mutableStateOf(username.isNotEmpty() && android.util.Patterns.EMAIL_ADDRESS.matcher(username).matches())
    }*/
    LaunchedEffect(key1 = context) {
        viewModel.events.collect { event ->
            if(event == UiEvent.LoginSuccess){
                onLoginClick()
            }
        }
    }
    val focusManager = LocalFocusManager.current
    val focusPassword = remember { FocusRequester() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = state.userName,
            onValueChange = { onEvent(LoginEvent.SetUserName(it)) },
            label = { Text("Username (Email)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { focusPassword.requestFocus() }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        TextField(
            value = state.password,
            onValueChange = { onEvent(LoginEvent.SetPassword(it)) },
            label = { Text("Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() },
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .focusRequester(focusPassword)
        )
        if (state.error != null) {
            Text(
                text = state.error.asString(context),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        Button(
            onClick = {
                onEvent(LoginEvent.Login)
            },
            enabled = !state.loading,
            modifier = Modifier.padding(vertical = 16.dp)
        ) {
            Text("Login")
        }
    }
}
