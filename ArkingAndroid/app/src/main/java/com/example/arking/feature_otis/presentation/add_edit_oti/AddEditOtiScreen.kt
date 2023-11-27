package com.example.arking.feature_otis.presentation.add_edit_oti

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.material.icons.outlined.AddCircleOutline
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.PhotoAlbum
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.arking.R
import com.example.arking.feature_otis.domain.model.OtiConcepts
import com.example.arking.feature_otis.domain.utils.ConceptType
import com.example.arking.feature_otis.domain.utils.SignType
import com.example.arking.ui.components.ErrorSnackbar
import com.example.arking.ui.components.ImageFromPath
import com.example.arking.ui.components.MyDatePickerDialog
import com.example.arking.ui.components.TextBold
import com.example.arking.ui.components.Textarea
import com.example.arking.ui.components.Title
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import java.util.UUID

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddEditOtiScreen(
    onBackClick: () -> Unit = {},
    viewModel: AddEditOtiViewModel = hiltViewModel()
) {
    var oti = viewModel.otiWithConcepts.collectAsState()
    val state = viewModel.state.value
    val snackbarHostState = remember { SnackbarHostState() }
    val onEvent = viewModel::onEvent
    val context = LocalContext.current
    val stringSucesss = stringResource(R.string.save_succes)
    LaunchedEffect(key1 = context) {
        viewModel.saveEvent.collect { event ->
            if(event == AddEditOtiViewModel.UiEvent.SaveOti){
                Toast.makeText(
                    context,
                    stringSucesss,
                    Toast.LENGTH_LONG
                ).show()
                onBackClick()
            }
        }
    }
    Scaffold(
        snackbarHost = {
            SnackbarHost(
                snackbarHostState,
            ){snackbarData->
                ErrorSnackbar(
                    snackbarData = snackbarData
                )
            }
       },
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(stringResource(id = R.string.otis_title))
                },
                navigationIcon = {
                    IconButton(onClick = { onBackClick() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                },
                actions = {
                    IconButton(onClick = { onEvent(AddEditOtiEvent.SaveOti) }) {
                        Icon(imageVector = Icons.Filled.Save, contentDescription = "Save oti")
                    }
                }
            )
        },
    ) {innerPadding->
        if(state.isAddingEditingConcept){
            AddEditOtiForm(state,onEvent)
        }
        else if(state.isSingning){
            SignOtiForm(state,onEvent)
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
    val focusManager = LocalFocusManager.current
    val focusComments = remember { FocusRequester() }
    val weigth = .15f
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
                        Column {
                            Textarea(state.description,
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                                keyboardActions = KeyboardActions(
                                    onNext = { focusComments.requestFocus() }
                                )
                            ){ newValue->
                                onEvent(AddEditOtiEvent.SetDescription(newValue))
                            }
                            if (state.descriptionError != null) {
                                Text(
                                    text = state.descriptionError.asString(),
                                    color = MaterialTheme.colorScheme.error,
                                )
                            }
                        }

                    }
                    Row {
                        Text(
                            text = stringResource(id = R.string.comments),
                        )
                        Textarea(state.comments,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.clearFocus() },
                            ),
                            modifier = Modifier.focusRequester(focusComments)){newValue->
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
                val total = state.concepts.filter { it.conceptType == ConceptType.Concept.toString() }.sumOf { it.total }
                val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
                numberFormat.maximumFractionDigits = 2
                Column(modifier = Modifier
                    .padding(16.dp, 0.dp)
                    ,
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier, verticalAlignment = Alignment.CenterVertically,horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(modifier = Modifier
                            .weight(1f)
                            .fillMaxWidth()) {
                            Title(text = stringResource(id = R.string.concepts)+ " (" + numberFormat.format(total) + ")")
                        }
                        IconButton(onClick = { onEvent(AddEditOtiEvent.AddConcept(ConceptType.Concept)) }) {
                            Icon(imageVector = Icons.Filled.Add, contentDescription = "Save oti",tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
                Column(modifier = Modifier
                    .padding(16.dp, 0.dp)
                    ,
                    verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    Row(modifier = Modifier, horizontalArrangement = Arrangement.SpaceBetween) {
                        TextBold(text = stringResource(id = R.string.concept),modifier = Modifier.weight(0.40f))
                        TextBold(text = stringResource(id = R.string.unit),modifier = Modifier.weight(0.12f))
                        TextBold(text = stringResource(id = R.string.unit_price),modifier = Modifier.weight(0.12f))
                        TextBold(text = stringResource(id = R.string.quantity),modifier = Modifier.weight(0.12f))
                        TextBold(text = stringResource(id = R.string.amount),modifier = Modifier.weight(0.12f))
                    }
                }
                if(!state.concepts.any()){
                    EmptyList()
                }
                
            }
            items(state.concepts.filter { it.conceptType == ConceptType.Concept.toString() }){concept ->
                ConceptItem(concept, state = state, onEvent)
            }
            item {
                Column {

                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center) {
                        if(state.signResident != null){
                            ImageFromPath(
                                filePath = state.signResident,
                                modifier = Modifier.width(300.dp).height(300.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))
                        if(state.signAuditor != null){
                            ImageFromPath(
                                filePath = state.signAuditor,
                                modifier = Modifier.width(300.dp).height(300.dp)
                            )
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center) {
                        Button(onClick = { onEvent(AddEditOtiEvent.SingOti(SignType.Resident)) }) {
                            Text(text = stringResource(id = R.string.sign_resident))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = { onEvent(AddEditOtiEvent.SingOti(SignType.Lega)) }) {
                            Text(text = stringResource(id = R.string.sign_lega))
                        }
                    }
                }
            }
        }

    }

}

@Composable
private fun ConceptTitle(
    onEvent: (AddEditOtiEvent) -> Unit,
    state: AddEditOtiState,
    conceptType: ConceptType,
    title:String,
    conceptParentId: UUID? = null
) {
    val total = state.concepts.filter { it.conceptType == conceptType.toString() }.sumOf { it.total }
    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
    numberFormat.maximumFractionDigits = 2
    Column(
        modifier = Modifier
            .padding(32.dp,0.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier
                .weight(1f)
                .fillMaxWidth()) {
                TextBold(text = title + " (" + numberFormat.format(total) + ")")
            }
            IconButton(
                onClick = { onEvent(AddEditOtiEvent.AddConcept(conceptType, conceptParentId)) },
                modifier = Modifier
                ) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "Save oti",tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
    if (!state.concepts.any { it.conceptType == conceptType.toString() }) {
        EmptyList()
    }
}

@Composable
private fun ConceptItem(
    concept: OtiConcepts,
    state: AddEditOtiState,
    onEvent: (AddEditOtiEvent) -> Unit
) {
    val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())
    numberFormat.maximumFractionDigits = 2
    Column(modifier = Modifier.border(width = 1.dp, color = MaterialTheme.colorScheme.outline)) {
        Row(modifier = Modifier
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(16.dp, 0.dp), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = concept.concept, modifier = Modifier.weight(0.40f))
            Text(text = concept.unit, modifier = Modifier.weight(0.11f))
            Text(text = numberFormat.format(concept.unitPrice), textAlign = TextAlign.End, modifier = Modifier.weight(0.11f))
            Text(text = concept.quantity.toString(), textAlign = TextAlign.End, modifier = Modifier.weight(0.11f))
            Text(text = numberFormat.format(concept.subtotal), textAlign = TextAlign.End, modifier = Modifier.weight(0.11f))
            IconButton(onClick = { onEvent(AddEditOtiEvent.EditConcept(concept)) }) {
                Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Update",tint = MaterialTheme.colorScheme.primary)
            }
            IconButton(onClick = { onEvent(AddEditOtiEvent.DeleteConcept(concept)) }) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete",tint = MaterialTheme.colorScheme.primary)
            }
        }
        ConceptTitle(onEvent,state, ConceptType.Material, stringResource(id = R.string.materials),concept.id)
        state.concepts.filter {
            it.conceptType == ConceptType.Material.toString() &&
                    it.parentConceptId == concept.id}.forEach { item ->
            Row(modifier = Modifier
                .padding(16.dp,0.dp), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = item.concept, modifier = Modifier.weight(0.40f))
                Text(text = item.unit, modifier = Modifier.weight(0.11f))
                Text(text = numberFormat.format(item.unitPrice), textAlign = TextAlign.End, modifier = Modifier.weight(0.11f))
                Text(text = item.quantity.toString(),textAlign = TextAlign.End, modifier = Modifier.weight(0.11f))
                Text(text = numberFormat.format(item.total), textAlign = TextAlign.End, modifier = Modifier.weight(0.11f))
                IconButton(onClick = { onEvent(AddEditOtiEvent.EditConcept(item)) }) {
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Update",tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { onEvent(AddEditOtiEvent.DeleteConcept(item)) }) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete",tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
        ConceptTitle(onEvent,state, ConceptType.Workforce, stringResource(id = R.string.workforce),concept.id)
        state.concepts.filter {
            it.conceptType == ConceptType.Workforce.toString() &&
                    it.parentConceptId == concept.id}.forEach { item ->
            Row(modifier = Modifier
                .padding(16.dp,0.dp), horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically) {
                Text(text = item.concept, modifier = Modifier.weight(0.40f))
                Text(text = item.unit, modifier = Modifier.weight(0.11f))
                Text(text = numberFormat.format(item.unitPrice), textAlign = TextAlign.End, modifier = Modifier.weight(0.11f))
                Text(text = item.quantity.toString(),textAlign = TextAlign.End, modifier = Modifier.weight(0.11f))
                Text(text = numberFormat.format(item.total), textAlign = TextAlign.End, modifier = Modifier.weight(0.11f))
                IconButton(onClick = { onEvent(AddEditOtiEvent.EditConcept(item)) }) {
                    Icon(imageVector = Icons.Outlined.Edit, contentDescription = "Update",tint = MaterialTheme.colorScheme.primary)
                }
                IconButton(onClick = { onEvent(AddEditOtiEvent.DeleteConcept(item)) }) {
                    Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete",tint = MaterialTheme.colorScheme.primary)
                }
            }
        }
        if(state.concepts.any { it.conceptType == ConceptType.Workforce.toString() && it.parentConceptId == concept.id }){
            ConceptTitle(onEvent,state, ConceptType.Tool, stringResource(id = R.string.tools),concept.id)
            if(!state.concepts.any {
                    it.conceptType == ConceptType.Tool.toString() &&
                            it.parentConceptId == concept.id})
            {
                Row(horizontalArrangement = Arrangement.Center) {
                    Button(onClick = { onEvent(AddEditOtiEvent.AddOtiConceptsToolDefault(concept.id)) }) {
                        Text(text = stringResource(id = R.string.add_tools_default))
                    }
                }
            }
            state.concepts.filter {
                it.conceptType == ConceptType.Tool.toString() &&
                        it.parentConceptId == concept.id}.forEach { item ->
                Row(modifier = Modifier
                    .padding(16.dp,0.dp), horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(text = item.concept, modifier = Modifier.weight(0.40f))
                    Text(text = item.unit, modifier = Modifier.weight(0.11f))
                    Text(text = numberFormat.format(item.unitPrice), textAlign = TextAlign.End, modifier = Modifier.weight(0.11f))
                    Text(text = item.quantity.toString(),textAlign = TextAlign.End, modifier = Modifier.weight(0.11f))
                    Text(text = numberFormat.format(item.total), textAlign = TextAlign.End, modifier = Modifier.weight(0.11f))
                    IconButton(onClick = { onEvent(AddEditOtiEvent.DeleteConcept(item)) }) {
                        Icon(imageVector = Icons.Outlined.Delete, contentDescription = "Delete",tint = MaterialTheme.colorScheme.primary)
                    }
                }
            }
        }
        Row(modifier = Modifier
            .padding(32.dp,0.dp), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.cost_direct),textAlign = TextAlign.End, modifier = Modifier.weight(0.80f), fontWeight = FontWeight.Bold)
            Text(text = numberFormat.format(concept.subtotal), textAlign = TextAlign.End, modifier = Modifier.weight(0.20f))
        }
        Row(modifier = Modifier
            .padding(32.dp,0.dp), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.unique_indirect),textAlign = TextAlign.End, modifier = Modifier.weight(0.80f), fontWeight = FontWeight.Bold)
            Text(text = numberFormat.format(concept.total - concept.subtotal), textAlign = TextAlign.End, modifier = Modifier.weight(0.20f))
        }
        Row(modifier = Modifier
            .padding(32.dp,0.dp), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
            Text(text = stringResource(id = R.string.cost_indirect),textAlign = TextAlign.End, modifier = Modifier.weight(0.80f), fontWeight = FontWeight.Bold)
            Text(text = numberFormat.format(concept.total), textAlign = TextAlign.End, modifier = Modifier.weight(0.20f))
        }
        Spacer(modifier = Modifier.height(8.dp))
    }


}

@Composable
private fun EmptyList() {
    Column(
        modifier = Modifier
            .padding(16.dp, 16.dp)
    ) {
        Row(modifier = Modifier) {
            Text(text = stringResource(id = R.string.empty_list))
        }
    }
}