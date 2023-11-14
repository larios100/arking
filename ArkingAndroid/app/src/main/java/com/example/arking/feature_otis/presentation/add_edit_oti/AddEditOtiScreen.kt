package com.example.arking.feature_otis.presentation.add_edit_oti

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.arking.R
import com.example.arking.feature_otis.domain.utils.ConceptType
import com.example.arking.ui.components.MyDatePickerDialog
import com.example.arking.ui.components.Title
import com.example.arking.ui.test.Textarea

@Composable
fun AddEditOtiScreen(
    viewModel: AddEditOtiViewModel = hiltViewModel()
) {
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val onEvent = viewModel::onEvent
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    //onEvent(AddEditOtiEvent.)
                },
                //backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Filled.Save, contentDescription = "Save oti")
            }
        },
    ) {innerPadding->
        if(state.isAddingEditingConcept){
            AddEditOtiForm(state.addingEditingConceptType,onEvent)
        }
        Box(modifier = Modifier.padding(innerPadding)){
            AddEditOtiContent(state,onEvent)
        }
    }
}

@Composable
fun AddEditOtiContent(state: AddEditOtiState, onEvent: (AddEditOtiEvent) -> Unit){
    var showDatePicker by remember {
        mutableStateOf(false)
    }
    var showDatePickerStart by remember {
        mutableStateOf(false)
    }
    var showDatePickerEnd by remember {
        mutableStateOf(false)
    }
    Column(modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)){
        LazyColumn(){
            item {
                Column(
                    modifier = Modifier
                        .padding(16.dp, 16.dp)
                    ,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Row {
                        Text(
                            text = stringResource(id = R.string.description),
                        )
                        Textarea(state.description){newValue->
                            onEvent(AddEditOtiEvent.SetDescription(newValue))
                        }
                    }
                    Row {
                        Text(
                            text = stringResource(id = R.string.comments),
                        )
                        Textarea(state.comments){newValue->
                            onEvent(AddEditOtiEvent.SetComments(newValue))
                        }
                    }
                    Row(modifier = Modifier.padding(16.dp, 16.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(id = R.string.date),
                            fontSize = 16.sp,
                            modifier = Modifier.weight(.08f)
                        )
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(.20f)) {
                            Button(onClick = { showDatePicker = true }) {
                                Text(text = state.date)
                            }
                        }
                        if (showDatePicker) {
                            MyDatePickerDialog(
                                onDateSelected = {
                                    onEvent(AddEditOtiEvent.SetDate(it))
                                },
                                onDismiss = { showDatePicker = false }
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.start_date),
                            fontSize = 16.sp,
                            modifier = Modifier.weight(.13f)
                        )
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(.19f)) {
                            Button(onClick = { showDatePickerStart = true }) {
                                Text(text = state.startDate)
                            }
                        }
                        if (showDatePickerStart) {
                            MyDatePickerDialog(
                                onDateSelected = {
                                    onEvent(AddEditOtiEvent.SetStartDate(it))
                                },
                                onDismiss = { showDatePickerStart = false }
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.end_part),
                            fontSize = 16.sp,
                            modifier = Modifier.weight(.12f)
                        )
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(.18f)) {
                            Button(onClick = { showDatePickerEnd = true }) {
                                Text(text = state.endDate)
                            }
                        }
                        if (showDatePickerEnd) {
                            MyDatePickerDialog(
                                onDateSelected = {
                                    onEvent(AddEditOtiEvent.SetEndDate(it))
                                },
                                onDismiss = { showDatePickerEnd = false }
                            )
                        }
                    }
                }
            }
            item {
                Column(modifier = Modifier
                    .padding(16.dp, 16.dp)
                    ,
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween) {
                        Title(text = stringResource(id = R.string.concepts))
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { onEvent(AddEditOtiEvent.AddConcept(ConceptType.Concept)) }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "Save oti")
                        }
                    }
                }
                Column(modifier = Modifier
                    .padding(16.dp, 16.dp)
                    ,
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                        Text(text = stringResource(id = R.string.concept),modifier = Modifier.weight(.15f))
                        Text(text = stringResource(id = R.string.unit),modifier = Modifier.weight(.15f))
                        Text(text = stringResource(id = R.string.unit_price),modifier = Modifier.weight(.15f))
                        Text(text = stringResource(id = R.string.op),modifier = Modifier.weight(.15f))
                        Text(text = stringResource(id = R.string.quantity),modifier = Modifier.weight(.15f))
                        Text(text = stringResource(id = R.string.amount),modifier = Modifier.weight(.15f))
                    }
                }
                if(!state.concepts.any()){
                    Column(modifier = Modifier
                        .padding(16.dp, 16.dp)) {
                        Row(modifier = Modifier) {
                            Text(text = stringResource(id = R.string.empty_list))
                        }
                    }
                }
                
            }
            items(state.concepts){concept ->
                Row(modifier = Modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                    Text(text = concept.concept)
                    Text(text = concept.unit)
                    Text(text = concept.unitPrice.toString())
                    Text(text = "")
                    Text(text = concept.quantity.toString())
                    Text(text = concept.amount.toString())
                }
            }
            item {
                Column(modifier = Modifier
                    .padding(16.dp, 16.dp)
                    ,
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween) {
                        Title(text = stringResource(id = R.string.materials))
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { onEvent(AddEditOtiEvent.AddConcept(ConceptType.Material)) }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "Save oti")
                        }
                    }
                }
                if(!state.concepts.any()){
                    Column(modifier = Modifier
                        .padding(16.dp, 16.dp)) {
                        Row(modifier = Modifier) {
                            Text(text = stringResource(id = R.string.empty_list))
                        }
                    }
                }
            }
            item {
                Column(modifier = Modifier
                    .padding(16.dp, 16.dp)
                    ,
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween) {
                        Title(text = stringResource(id = R.string.workforce))
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { onEvent(AddEditOtiEvent.AddConcept(ConceptType.Workforce)) }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "Save oti")
                        }
                    }
                }
                if(!state.concepts.any()){
                    Column(modifier = Modifier
                        .padding(16.dp, 16.dp)) {
                        Row(modifier = Modifier) {
                            Text(text = stringResource(id = R.string.empty_list))
                        }
                    }
                }
            }
            item {
                Column(modifier = Modifier
                    .padding(16.dp, 16.dp)
                    ,
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween) {
                        Title(text = stringResource(id = R.string.tools))
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { onEvent(AddEditOtiEvent.AddConcept(ConceptType.Tool)) }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "Save oti")
                        }
                    }
                }
                if(!state.concepts.any()){
                    Column(modifier = Modifier
                        .padding(16.dp, 16.dp)) {
                        Row(modifier = Modifier) {
                            Text(text = stringResource(id = R.string.empty_list))
                        }
                    }
                }
            }
        }

    }

}