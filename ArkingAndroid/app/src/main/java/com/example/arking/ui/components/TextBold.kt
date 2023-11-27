package com.example.arking.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight

@Composable
fun TextBold(text: String, modifier: Modifier = Modifier) {
    Text(text = text,modifier = modifier,fontWeight = FontWeight.Bold)
}