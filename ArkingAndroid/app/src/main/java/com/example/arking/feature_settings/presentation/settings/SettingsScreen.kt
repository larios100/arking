package com.example.arking.feature_settings.presentation.settings

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.arking.R

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val onEvent = viewModel::onEvent
    val context = LocalContext.current
    val stringSucesss = stringResource(R.string.save_succes)
    LaunchedEffect(key1 = context) {
        viewModel.events.collect { event ->
            if(event == UiEvent.SyncSuccess){
                Toast.makeText(
                    context,
                    stringSucesss,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { onEvent(SettingsEvents.Sync) },
            enabled = !state.loading) {
            Text(text = stringResource(id = R.string.sync_download))
        }
        if (state.error != null) {
            Text(
                text = state.error.asString(context),
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }
        if(state.loading){
            CircularProgressIndicator(
                modifier = Modifier.width(64.dp),
                color = MaterialTheme.colorScheme.secondary,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
            Text(text = stringResource(id = R.string.sync_inprogress))
        }
    }
}