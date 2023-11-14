package com.example.arking

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.arking.ui.theme.ArkingTheme
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import java.util.concurrent.Executor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArkingApp(
    outputDirectory: File,
    executor: Executor,
    applicationContext: Context,
    onImageCaptured: (Uri) -> Unit,
    onError: (ImageCaptureException) -> Unit
) {
    ArkingTheme {
        val navController: NavHostController = rememberNavController()
        var buttonsVisible = remember { mutableStateOf(true) }
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Arking")
                    }
                )
            },
            bottomBar = {
                BottomBar(
                    navController = navController,
                    state = buttonsVisible,
                    modifier = Modifier
                )
            }
        ) { innerPadding ->
            Box(Modifier.padding(innerPadding)) {
                ArkingNavGraph(navController = navController,outputDirectory,executor,onImageCaptured,onError,applicationContext)
            }
        }
    }
}