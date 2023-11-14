package com.example.arking.ui.contracts

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.arking.Camera
import com.example.arking.Contracts
import com.example.arking.PartDetail
import com.example.arking.Signature
import com.example.arking.model.TaskHeader
import com.example.arking.model.TaskRoot
import com.example.arking.utils.ReadJSONFromAssets
import com.google.gson.Gson

@Composable
fun TaskScreen(
    navController: NavController,
    id: Int
) {
    //val databaseHelper = remember { DatabaseHelper(LocalContext.current) }
    val json = ReadJSONFromAssets(LocalContext.current,"TaskList.json")
    val data = Gson().fromJson(json, TaskRoot::class.java)
    TasksContent(data.headers)
    /*Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.DarkGray)
            .wrapContentSize(Alignment.Center)
    ) {
        Text(text = "Detalles de la actividad ${id}")

        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.navigate(Contracts.route) }) {
            Text(text = "Regresar")
        }
        Button(onClick = { navController.navigate(Camera.route) }) {
            Text("Tomar Foto")
        }
        Button(onClick = { navController.navigate(Signature.route) }) {
            Text("Firmar")
        }
        Button(onClick = { navController.navigate(PartDetail.route) }) {
            Text("Detalle")
        }
    }*/
}

@Composable
fun TasksContent(headers: List<TaskHeader>){
    var selectedTabIndex by remember { mutableStateOf(0) }
    Column {
        TabsHeader(headers, selectedTabIndex){
            tabIndex -> selectedTabIndex = tabIndex
        }
    }
}

@Composable
fun TabsHeader(headers: List<TaskHeader>,
               selectedTabIndex: Int,
               onTabClick: (Int) -> Unit){
    ScrollableTabRow(selectedTabIndex = selectedTabIndex, modifier = Modifier.fillMaxWidth()) {
        headers.forEachIndexed { tabIndex, tab ->
            Tab(
                selected = selectedTabIndex == tabIndex,
                onClick = { onTabClick(tabIndex) },
                text = { Text(text = tab.name) }
            )
        }
    }
}

