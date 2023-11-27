package com.example.arking.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import coil.compose.rememberAsyncImagePainter
import coil.compose.rememberImagePainter
import java.io.File

@Composable
fun ImageFromPath(filePath: String, modifier: Modifier = Modifier) {
    val painter = rememberAsyncImagePainter(model = File(filePath))

    Image(
        painter = painter,
        modifier = modifier,
        contentDescription = null // Puedes proporcionar una descripción aquí
    )
}