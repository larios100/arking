package com.example.arking.ui.part

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material.icons.outlined.KeyboardArrowRight
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.PhotoAlbum
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.arking.model.GalleryItem
import com.example.arking.model.Part
import com.example.arking.model.PartAttachment
import com.example.arking.model.PartTask
import com.example.arking.model.Task
import com.example.arking.model.TaskCategory
import com.example.arking.model.TaskHeader
import com.example.arking.model.TaskRoot
import com.example.arking.model.Test
import com.example.arking.presentation.parts.PartEvent
import com.example.arking.presentation.parts.PartViewModel
import com.example.arking.ui.components.PartGallery
import com.example.arking.utils.ReadJSONFromAssets
import com.google.gson.Gson


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartScreen(
    navController: NavController,
    viewModel: PartViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val onEvent = viewModel::onEvent
    val json = ReadJSONFromAssets(LocalContext.current,"TaskList.json")
    val data = Gson().fromJson(json, TaskRoot::class.java)
    if(state.part != null){
        Scaffold(
            topBar = {
                TopAppBar(
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(state.part!!.name)
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.primary)
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("camera/" + state.part!!.id) }) {
                            Icon(imageVector = Icons.Rounded.AddAPhoto, contentDescription = "Take photo", tint = MaterialTheme.colorScheme.primary)
                        }
                        IconButton(onClick = { onEvent(PartEvent.ToggleGallery) }) {
                            Icon(imageVector = Icons.Rounded.PhotoAlbum, contentDescription = "Ver fotos", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                )
            }
        ) { innerPadding->
            Box(Modifier.padding(innerPadding)){
                if(state.showGallery){
                    PartGallery(ConvertPartAttachmentToGalleryItem(state.partAttachments))
                }
                else{
                    PartContent(state.part!!,data.headers, state.partTasks,onEvent, navController)
                }
            }
        }
    }
    else{
        Text(text = "Registro no encontrado")
    }
}


@Composable
fun PartContent(
    part: Part,
    headers: List<TaskHeader>,
    partsTasks: List<PartTask>,
    onEvent: (PartEvent) -> Unit,
    navController: NavController
){
    var selectedTabIndex by remember { mutableStateOf(0) }
    var selectedTaskHeaderId by remember { mutableStateOf(headers.first()) }
    Column {
        TabsHeader(headers, selectedTabIndex) { tabIndex, taskId ->
            selectedTabIndex = tabIndex
            selectedTaskHeaderId = taskId
        }
        var categoryExpanded by remember {
            mutableStateOf<TaskCategory?>(null)
        }
        LazyColumn(){
            selectedTaskHeaderId.categories.forEach{ category ->
                val expanded = category == categoryExpanded
                item {
                    CategoryItem(category, expanded){ category->
                        categoryExpanded = if (category == categoryExpanded) null else category
                    }
                }
                if(expanded){
                    items(category.tasks){ task ->
                        TaskItem(partsTasks, part, task, onEvent, navController)
                    }
                }
            }
        }
        LazyColumn(){
            items(selectedTaskHeaderId.tests){test ->
                TestList(test = test){testSelected->
                    navController.navigate("part_test/" + part.id + "/" + testSelected.id)
                }
            }
        }
    }
}

@Composable
private fun TestList(test: Test, onTabClick: (Test) -> Unit){
    Column(
        modifier = Modifier
            .padding(8.dp)
            .border(2.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .clickable { onTabClick(test) }
                .padding(16.dp).fillMaxWidth(),

            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)

        ) {
            Text(text = test.name, modifier = Modifier.weight(1f))
            Icon(
                Icons.Outlined.KeyboardArrowRight,
                contentDescription = "arrow-right",
                tint = Color.Gray
            )
        }
    }
}

@Composable
private fun TaskItem(
    partsTasks: List<PartTask>,
    part: Part,
    task: Task,
    onEvent: (PartEvent) -> Unit,
    navController: NavController
) {
    Column(modifier = Modifier.padding(16.dp, 4.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
        ) {
            var initialCheked =
                if (partsTasks.any { it.partId == part.id && it.taskId == task.id }) partsTasks.first { it.partId == part.id && it.taskId == task.id }.isCompleted else false
            var checked by remember {
                mutableStateOf(initialCheked)
            }
            Checkbox(checked = checked, onCheckedChange = {
                checked = it
                onEvent(PartEvent.SetCompleted(task.id,it))
            })
            Text(text = task.name, modifier = Modifier.weight(1f))
            IconButton(onClick = { navController.navigate("camera_task/" + part.id + "/" + task.id) }) {
                Icon(
                    imageVector = Icons.Rounded.AddAPhoto,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            IconButton(onClick = { navController.navigate("part_task/" + part.id + "/" + task.id) }) {
                Icon(
                    imageVector = Icons.Rounded.PhotoAlbum,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun CategoryItem(
    category: TaskCategory,
    expanded: Boolean,
    onTabClick: (TaskCategory) -> Unit
) {
    Column(
        modifier = Modifier
            .padding(8.dp)
            .border(2.dp, Color.LightGray, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
        ) {
            Text(text = category.name, modifier = Modifier.weight(1f))
            val icon = if (expanded) Icons.Default.ArrowDropUp else Icons.Default.ArrowDropDown
            IconButton(onClick = {
                onTabClick(category)
            }) {
                Icon(imageVector = icon, contentDescription = null)
            }
        }
    }
}

@Composable
fun TabsHeader(headers: List<TaskHeader>,
               selectedTabIndex: Int,
               onTabClick: (Int, TaskHeader) -> Unit){
    ScrollableTabRow(selectedTabIndex = selectedTabIndex, modifier = Modifier.fillMaxWidth()) {
        headers.forEachIndexed { tabIndex, tab ->
            Tab(
                selected = selectedTabIndex == tabIndex,
                onClick = { onTabClick(tabIndex, tab) },
                text = { Text(text = tab.name) }
            )
        }
    }
}

fun ConvertPartAttachmentToGalleryItem(attachs: List<PartAttachment>): List<GalleryItem>{
    var result: ArrayList<GalleryItem> = ArrayList<GalleryItem>()
    attachs.forEach(){ attach ->
        result.add(GalleryItem(attach.partId,attach.path))
    }
    return result
}


