package com.example.arking.feature_otis.presentation.otis

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.arking.R
import com.example.arking.feature_otis.domain.model.Oti
import com.example.arking.feature_otis.util.Screen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OtisScreen(
    navController: NavController,
    viewModel: OtisViewModel = hiltViewModel()

) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screen.AddEditNoteOti.route)
                },
                //backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add note")
            }
        },
    ) {innerPadding->
        Box(modifier = Modifier.padding(innerPadding)){
            if(state.otis.any()){
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(state.otis) { oti ->
                        OtiItem(
                            oti = oti,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    navController.navigate(
                                        ""
                                    )
                                },
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
            else{
                Text(text = stringResource(id = R.string.empty_list))
            }
        }

    }
}

@Composable
fun OtiItem(oti: Oti, modifier: Modifier) {
    Surface(
        color = Color.White,
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(1.dp, Color.LightGray),
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) {
                Text(oti.description, Modifier, color = Color.Black, fontSize = 20.sp)
                Text(oti.date, Modifier, color = Color.Gray)
                Text(oti.startDate, Modifier, color = Color.Gray)
            }
            Icon(
                Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = "arrow-right",
                tint = Color.Gray
            )
        }
    }
}
