package com.example.arking

import android.content.Context
import android.net.Uri
import androidx.camera.core.ImageCaptureException
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.arking.feature_otis.presentation.add_edit_oti.AddEditOtiScreen
import com.example.arking.feature_otis.presentation.otis.OtisScreen
import com.example.arking.ui.camera.CameraScreen
import com.example.arking.ui.camera.CameraScreen2
import com.example.arking.ui.camera.CameraTaskScreen
import com.example.arking.ui.camera.CameraTestScreen
import com.example.arking.ui.contracts.ContractsScreen
import com.example.arking.ui.contracts.TaskScreen
import com.example.arking.ui.part.PartScreen
import com.example.arking.ui.part.PartTaskScreen
import com.example.arking.ui.part.PartsScreen
import com.example.arking.ui.prototypes.PrototypesScreen
import com.example.arking.ui.settings.SettingsScreen
import com.example.arking.ui.signature.SignatureScreen
import com.example.arking.ui.test.TestScreen
import java.io.File
import java.lang.Integer.parseInt
import java.util.concurrent.Executor

@Composable
fun ArkingNavGraph(navController: NavHostController,
                   outputDirectory: File,
                   executor: Executor,
                   onImageCaptured: (Uri) -> Unit,
                   onError: (ImageCaptureException) -> Unit,
                   applicationContext: Context
) {
    NavHost(navController, startDestination = "home") {
        navigation(startDestination = Contracts.route, route= "home"){
            composable(Contracts.route) {
                ContractsScreen(onContractClick = {
                    navController.navigate("contracts/${it.id}")
                })
            }
            composable(ContractDetail.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType })) { backStackEntry ->
                PartsScreen(onBackClick = {
                    navController.navigateUp()
                }, onPartClick = {
                    navController.navigate("tasks/${it.id}")
                })
            }
            composable(Tasks.route,
                arguments = listOf(navArgument("id") { type = NavType.StringType })) { backStackEntry ->
                PartScreen(navController)
            }
        }
        composable(Prototypes.route) {
            PrototypesScreen()
        }
        composable(Settings.route) {
            SettingsScreen(navController)
        }
        composable(Signature.route) {
            SignatureScreen()
        }
        composable(PartDetail.route) {
            //PartScreen()
        }
        composable(Camera.route,
            arguments = listOf(navArgument("id") { type = NavType.StringType })) { backStackEntry ->
            CameraScreen2(applicationContext = applicationContext,outputDirectory,parseInt(backStackEntry.arguments?.getString("id")),navController)
        }
        composable("camera_task/{partId}/{taskId}",
            arguments = listOf(
                navArgument("partId") { type = NavType.StringType },
                navArgument("taskId") { type = NavType.StringType }),
        ) { backStackEntry ->
            CameraTaskScreen(applicationContext = applicationContext,
                outputDirectory,
                parseInt(backStackEntry.arguments?.getString("partId")),
                parseInt(backStackEntry.arguments?.getString("taskId")),navController)
        }
        composable("part_task/{partId}/{taskId}",
            arguments = listOf(
                navArgument("partId") { type = NavType.StringType },
                navArgument("taskId") { type = NavType.StringType }),
        ) { backStackEntry ->
            PartTaskScreen(navController,
                parseInt(backStackEntry.arguments?.getString("partId")),
                parseInt(backStackEntry.arguments?.getString("taskId")))
        }
        composable("part_test/{partId}/{testId}",
            arguments = listOf(
                navArgument("partId") { type = NavType.StringType },
                navArgument("testId") { type = NavType.StringType }),
        ) { backStackEntry ->
            TestScreen(navController,
                parseInt(backStackEntry.arguments?.getString("partId")),
                parseInt(backStackEntry.arguments?.getString("testId")))
        }
        composable("camera_test/{partId}/{testId}",
            arguments = listOf(
                navArgument("partId") { type = NavType.StringType },
                navArgument("testId") { type = NavType.StringType }),
        ) { backStackEntry ->
            CameraTestScreen(applicationContext = applicationContext,
                outputDirectory,
                parseInt(backStackEntry.arguments?.getString("partId")),
                parseInt(backStackEntry.arguments?.getString("testId")),navController)
        }
        composable(com.example.arking.feature_otis.util.Screen.OtisScreen.route){
            OtisScreen(navController = navController)
        }
        composable(com.example.arking.feature_otis.util.Screen.AddEditNoteOti.route
                +
                "?otiId={otiId}"){
            AddEditOtiScreen()
        }
    }
}
@Composable
fun BottomBar(
    navController: NavHostController, state: MutableState<Boolean>, modifier: Modifier = Modifier
) {
    val screens = arkingTabRowScreens

    NavigationBar(
        modifier = modifier,
        containerColor = Color.White,
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        screens.forEach { screen ->

            NavigationBarItem(
                label = {
                    Text(text = screen.title!!)
                },
                icon = {
                    Icon(imageVector = screen.icon!!, contentDescription = "")
                },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    unselectedTextColor = Color.Gray, selectedTextColor = Color.Blue
                ),
            )
        }
    }

}