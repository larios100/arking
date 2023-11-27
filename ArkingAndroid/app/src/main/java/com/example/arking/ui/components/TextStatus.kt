package com.example.arking.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.arking.R

@Composable
fun TextStatus(status: String, modifier: Modifier = Modifier) {
    var statusName  = stringResource(id = R.string.status_procesing)
    if(status == "Open")
        statusName = stringResource(id = R.string.status_open)
    if(status == "Pending")
        statusName = stringResource(id = R.string.status_pending)
    if(status == "Done")
        statusName = stringResource(id = R.string.status_done)
    if(status == "Canceled")
        statusName = stringResource(id = R.string.status_canceled)
    Text(text = statusName, color = getColorStatus(status), modifier = modifier)
}
private fun getColorStatus(status: String): Color {
    if(status == "Open")
        return Color.Blue
    if(status == "Pending")
        return Color.Gray
    if(status == "Done")
        return Color.Green
    if(status == "Canceled")
        return Color.Red
    return Color.Blue
}