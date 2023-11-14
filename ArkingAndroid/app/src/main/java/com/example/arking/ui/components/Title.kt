package com.example.arking.ui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.sp

@Composable
fun Title(text:String) {
    Text(text = text,color = Color.Black, fontSize = 20.sp)
}