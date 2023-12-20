package com.example.arking.ui.part

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.arking.model.Contract
import com.example.arking.model.PartWithPrototype
import com.example.arking.presentation.parts.PartsViewModel
import com.example.arking.ui.components.ListItem

/**
 * The Contracts screen.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartsScreen(
    onBackClick: () -> Unit = {},
    onPartClick: (PartWithPrototype) -> Unit = {},
    viewModel: PartsViewModel = hiltViewModel()
) {

    val state by viewModel.state.collectAsState()
    if(state.contract != null){
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                    ),
                    title = {
                        Text(state.contract!!.name)
                    },
                    navigationIcon = {
                        IconButton(onClick = { onBackClick() }) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onPrimaryContainer)
                        }
                    }
                )
            }
        ) { innerPadding->
            Box(Modifier.padding(innerPadding)){
                PartsContent(state.contract!!,state.parts,onPartClick)
            }
        }
    }
    else{
        Text(text = "Registro no encontrado")
    }

}

@Composable
fun PartsContent(
    contract:Contract,
    parts: List<PartWithPrototype>,
    onPartClick: (PartWithPrototype) -> Unit = {},
    modifier: Modifier = Modifier
) {
    Column(
        modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .height(50.dp)){
        Text(text = contract.description)
    }
    Column(modifier) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            content = {
                parts.forEach { item ->
                    item {
                        Column(modifier
                            .padding(horizontal = 8.dp, vertical = 8.dp)) {
                            ParteItem(model = item){
                                onPartClick(item)
                                //navController.navigate("tasks/"+ item.id)
                            }
                        }

                    }
                }
            }
        )
    }
}
@Composable
fun ParteItem(modifier: Modifier = Modifier, model: PartWithPrototype,onTapped: () -> Unit = {}) {
    ListItem(
    ) {
        Row(
            modifier = Modifier
                .clickable { onTapped() }
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f).fillMaxWidth()) {
                Text(model.name, Modifier, fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Text(model.description, Modifier)
                Text("Modelo: "+model.prototype, Modifier)
            }
            Icon(
                Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = "arrow-right",
                tint = Color.Gray
            )
        }
    }
}
