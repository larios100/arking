package com.example.arking.ui.prototypes

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Download
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.arking.model.Prototype
import com.example.arking.ui.components.ListItem

/**
 * The Contracts screen.
 */
@Composable
fun PrototypesScreen(
    onPrototypesScreen: (String) -> Unit = {},
    viewModel: PrototypesViewModel = hiltViewModel()
) {
    val prototypes by viewModel.prototypes.collectAsState(initial = emptyList())
    PrototypesContent(prototypes)
}
@Composable
fun PrototypesContent(prototypes: List<Prototype>,modifier: Modifier = Modifier){
    if(prototypes.any()){
        Column(modifier) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                content = {
                    prototypes.forEach { item ->
                        item {
                            Column(modifier
                                .padding(horizontal = 16.dp, vertical = 8.dp)) {
                                PrototypeItem(prototype = item)
                            }
                        }
                    }
                }
            )
        }
    }
    else{
        Text(text = "No se encontraron registros")
    }

}
@Composable
fun PrototypeItem(prototype: Prototype){
    ListItem(
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(prototype.name, Modifier.weight(1f))
            Icon(
                Icons.Outlined.Download,
                contentDescription = "download"
            )
        }
    }
}