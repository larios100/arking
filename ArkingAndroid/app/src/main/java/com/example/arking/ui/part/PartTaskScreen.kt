package com.example.arking.ui.part

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.arking.model.GalleryItem
import com.example.arking.model.TaskAttachment
import com.example.arking.model.TaskRoot
import com.example.arking.ui.components.PartGallery
import com.example.arking.utils.ReadJSONFromAssets
import com.google.gson.Gson

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PartTaskScreen(
    navController: NavController,
    partId: Int,
    taskId: Int,
    viewModel: PartsViewModel = hiltViewModel()
) {
    val taskAttachment by viewModel.getAllTaskAttachmentByPartId(partId,taskId).collectAsState(emptyList())
    val json = ReadJSONFromAssets(LocalContext.current,"TaskList.json")
    val data = Gson().fromJson(json, TaskRoot::class.java)
    var title by remember { mutableStateOf("") }
    data.headers.forEach() {header ->
        header.categories.forEach(){category ->
            category.tasks.forEach(){task ->
                if(task.id == taskId){
                    title = task.name
                }
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
                    Text(title)
                },
                navigationIcon = {
                    IconButton(onClick = { navController.navigateUp() }) {
                        Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back", tint = MaterialTheme.colorScheme.onPrimaryContainer)
                    }
                }
            )
        }
    ) { innerPadding->
        Box(Modifier.padding(innerPadding)){
            PartGallery(ConvertTakAttachmentToGalleryItem(taskAttachment))
        }
    }
}
fun ConvertTakAttachmentToGalleryItem(attachs: List<TaskAttachment>): List<GalleryItem>{
    var result: ArrayList<GalleryItem> = ArrayList<GalleryItem>()
    attachs.forEach(){ attach ->
        result.add(GalleryItem(attach.partId,attach.path))
    }
    return result
}
