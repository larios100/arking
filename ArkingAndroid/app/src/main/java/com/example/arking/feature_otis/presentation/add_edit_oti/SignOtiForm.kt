package com.example.arking.feature_otis.presentation.add_edit_oti

import android.util.Log
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.example.arking.ui.components.Signature

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SignOtiForm(
    state: AddEditOtiState,
    onEvent: (AddEditOtiEvent)-> Unit
) {
    Dialog(onDismissRequest = {
        onEvent(AddEditOtiEvent.CancelSingOti)
    }) {
        Signature({path ->
            onEvent(AddEditOtiEvent.SaveSingOti(path))
        }, {
            onEvent(AddEditOtiEvent.CancelSingOti)
        })
    }
}