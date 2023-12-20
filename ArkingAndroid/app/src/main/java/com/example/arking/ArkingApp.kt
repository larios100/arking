package com.example.arking

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.camera.core.ImageCaptureException
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.Logout
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.arking.feature_login.presentation.login.LoginScreen
import com.example.arking.utils.SessionManager
import java.io.File
import java.util.concurrent.Executor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArkingApp(
    activity: ComponentActivity,
    outputDirectory: File,
    applicationContext: Context,
) {
    val navController: NavHostController = rememberNavController()
    var buttonsVisible = remember { mutableStateOf(true) }
    val sessionManager = remember { SessionManager(activity) }
    if(sessionManager.isLoggedIn){
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    title = {
                        Text("Arking")
                    },
                    actions = {
                        IconButton(onClick = {
                            sessionManager.isLoggedIn = false
                            val i = Intent(applicationContext, MainActivity::class.java)
                            applicationContext.startActivity(i)
                            activity?.finish()
                        }) {
                            Icon(imageVector = Icons.AutoMirrored.Rounded.Logout, contentDescription = null)
                        }
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
                ArkingNavGraph(
                    navController = navController,
                    outputDirectory,
                    applicationContext)
            }
        }
    }
    else{
        LoginScreen(onLoginClick = {
            Log.i("Login","Suceess")
            sessionManager.isLoggedIn = true
            val i = Intent(applicationContext, MainActivity::class.java)
            applicationContext.startActivity(i)
            activity?.finish()
        })
    }

}