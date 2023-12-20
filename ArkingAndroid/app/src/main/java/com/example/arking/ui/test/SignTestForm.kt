package com.example.arking.ui.test

import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog
import com.example.arking.presentation.tests.TestEvent
import com.example.arking.presentation.tests.TestState
import com.example.arking.ui.components.Signature

@Composable
fun SignTestForm(
    onEvent: (TestEvent)-> Unit
) {
    Dialog(onDismissRequest = {
        onEvent(TestEvent.CancelSing)
    }) {
        Signature({path ->
            onEvent(TestEvent.SaveSing(path))
        }, {
            onEvent(TestEvent.CancelSing)
        })
    }
}