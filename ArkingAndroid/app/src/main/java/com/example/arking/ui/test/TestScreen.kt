package com.example.arking.ui.test

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.PhotoAlbum
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.arking.R
import com.example.arking.feature_otis.domain.utils.SignType
import com.example.arking.model.PartTestItem
import com.example.arking.model.TestWithDataItem
import com.example.arking.presentation.tests.TestEvent
import com.example.arking.presentation.tests.TestState
import com.example.arking.presentation.tests.TestViewModel
import com.example.arking.ui.components.ImageFromPath
import com.example.arking.ui.components.MyDatePickerDialog
import com.example.arking.ui.components.PartGallery
import com.example.arking.ui.components.Textarea
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestScreen(
    navController: NavController,
    partId: Int,
    testId: Int,
    viewModel: TestViewModel = hiltViewModel()
) {
    val parts by viewModel.parts.collectAsState()
    val state by viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    val context = LocalContext.current
    val stringSucesss = stringResource(R.string.save_succes)
    LaunchedEffect(key1 = context) {
        viewModel.saveEvent.collect { event ->
            if(event){
                Toast.makeText(
                    context,
                    stringSucesss,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                title = {
                    Text(viewModel.test.name)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                },
                actions = {
                    IconButton(onClick = { navController.navigate("camera_test/"+ partId + "/" + viewModel.test.id) }) {
                        Icon(imageVector = Icons.Rounded.AddAPhoto, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    IconButton(onClick = { onEvent(TestEvent.ToggleGallery) }) {
                        Icon(imageVector = Icons.Rounded.PhotoAlbum, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            )
        }
    ) { innerPadding->
        Box(Modifier.padding(innerPadding)){
            if(state.showGallery){
                PartGallery(state.photos)
            }
            else{
                if(state.isSinging){
                    SignTestForm(onEvent = onEvent)
                }
                TestContent(state, onEvent= onEvent, onTakePhotoItem = {
                    navController.navigate("camera_test_item/$partId/$it")
                },onGalleryItem = {
                    navController.navigate("test_item/$partId/$it")
                })
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TestContent(
    testState: TestState,
    onEvent: (TestEvent) -> Unit,
    onTakePhotoItem: (Int) -> Unit,
    onGalleryItem: (Int) -> Unit
){
    var updatedItems by remember { mutableStateOf(ArrayList<TestWithDataItem>()) }
    Column(modifier = Modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        var newPartTestItem = ArrayList<PartTestItem>()
        LazyColumn(){
            items(testState.items){ item->
                if(!updatedItems.any { it.id == item.id }){
                    updatedItems.add(item)
                }
                TestItem(item, onItemChanged = { updatedItem ->
                    val index = updatedItems.indexOfFirst { it.id == updatedItem.id }
                    if(index != -1){
                        updatedItems[index] = updatedItem
                    }
                    Log.i("onItemChanged", updatedItems.toString())
                },
                onTakePhotoItem,
                    onGalleryItem)
            }
            item {
                Column(
                    modifier = Modifier
                        .padding(16.dp, 16.dp)
                    ,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Row {
                        Text(
                            text = stringResource(id = R.string.comments),
                        )
                        Textarea(testState.comments){newValue->
                            onEvent(TestEvent.SetComments(newValue))
                        }
                    }
                }
                Column {

                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center) {
                        if(testState.signResident != null){
                            ImageFromPath(
                                filePath = testState.signResident,
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(300.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(16.dp))
                        if(testState.signAuditor != null){
                            ImageFromPath(
                                filePath = testState.signAuditor,
                                modifier = Modifier
                                    .width(300.dp)
                                    .height(300.dp)
                            )
                        }
                    }
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center) {
                        Button(onClick = { onEvent(TestEvent.SingTest(SignType.Resident)) }) {
                            Text(text = stringResource(id = R.string.sign_resident))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = { onEvent(TestEvent.SingTest(SignType.Lega)) }) {
                            Text(text = stringResource(id = R.string.sign_lega))
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        Button(onClick = {
                            onEvent(TestEvent.Save(updatedItems))
                        }) {
                            Text(text = stringResource(id = R.string.save))
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TestItem(
    item: TestWithDataItem,
    onItemChanged: (TestWithDataItem) -> Unit,
    onTakePhotoItem: (Int) -> Unit,
    onGalleryItem: (Int) -> Unit
) {
    var fixDate by remember { mutableStateOf(item.fixDate) }
    var testDate by remember { mutableStateOf(item.testDate) }
    var result by remember { mutableStateOf(item.result) }
    var validation by remember { mutableStateOf(item.validation) }
    Card(
        modifier = Modifier
            .padding(0.dp, 8.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        var showDatePicker by remember {
            mutableStateOf(false)
        }
        var showDatePickerFix by remember {
            mutableStateOf(false)
        }
        Row(
            modifier = Modifier.padding(16.dp, 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(item.name, Modifier, fontSize = 20.sp)
                    IconButton(onClick = { onTakePhotoItem(item.id) }) {
                        Icon(imageVector = Icons.Rounded.AddAPhoto, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                    IconButton(onClick = { onGalleryItem(item.id) }) {
                        Icon(imageVector = Icons.Rounded.PhotoAlbum, contentDescription = null, tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
                Text(item.description, Modifier)
            }
        }
        Row(
            modifier = Modifier.padding(16.dp, 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.test_date),
                fontSize = 16.sp,
                modifier = Modifier.weight(.25f)
            )
            Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(.25f)) {
                Button(onClick = { showDatePicker = true }) {
                    Text(text = testDate)
                }
            }
            if (showDatePicker) {
                MyDatePickerDialog(
                    onDateSelected = {
                        testDate = it
                        onItemChanged(item.copy(testDate = testDate, fixDate = fixDate, result = result, validation = validation))
                    },
                    onDismiss = { showDatePicker = false }
                )
            }
            Text(
                text = stringResource(id = R.string.test_fix_date),
                fontSize = 16.sp,
                modifier = Modifier.weight(.25f)
            )
            Box(contentAlignment = Alignment.Center, modifier = Modifier.weight(.25f)) {
                Button(onClick = { showDatePickerFix = true }) {
                    Text(text = fixDate)
                }
            }
            if (showDatePickerFix) {
                MyDatePickerDialog(
                    onDateSelected = {
                        //onEvent(TestEvent.SetFixDate(item.id, it))
                        fixDate = it
                        onItemChanged(item.copy(testDate = testDate, fixDate = fixDate, result = result, validation = validation))
                    },
                    onDismiss = { showDatePickerFix = false }
                )
            }
        }
        Row(
            modifier = Modifier.padding(16.dp, 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = stringResource(id = R.string.test_result))
            Log.i("Row", item.result)
            TextField(
                value = result,
                onValueChange = { value ->
                    //result = value
                    Log.i("TextField", value)
                    //onEvent(TestEvent.SetResult(index, value))
                    result = value
                    onItemChanged(item.copy(testDate = testDate, fixDate = fixDate, result = result, validation = validation))
                },
                modifier = Modifier
            )
            Text(text = stringResource(id = R.string.test_validation))
            Checkbox(
                checked = validation,
                onCheckedChange = { value ->
                    //onEvent(TestEvent.SetValidation(index, value))
                    validation = value
                    onItemChanged(item.copy(testDate = testDate, fixDate = fixDate, result = result, validation = validation))
                }
            )
        }
    }
}


private fun convertMillisToDate(millis: Long): String {
    Log.i("Date",  "millis " + millis.toString())
    val formatter = SimpleDateFormat("dd/MM/yyyy", Locale.ROOT)
    Log.i("Date",  "date  " + Date(millis).toString())
    return formatter.format(Date(millis))
}



@Composable
@Preview
fun TestContentPreview(){
    //TestContent(test = Test(1,"Prueba test", listOf(TestItem(1,"dedededrfgtv","orhoew v teiitengu vufnormo fiorjfiurwc owfriufhrwu"))))
}