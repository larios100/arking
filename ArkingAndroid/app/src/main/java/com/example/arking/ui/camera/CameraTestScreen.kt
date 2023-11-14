package com.example.arking.ui.camera

import android.content.Context
import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.arking.model.PartTestAttachment
import java.io.File
import java.util.UUID

@Composable
fun CameraTestScreen(applicationContext: Context,
                     outputDirectory: File,
                     partId: Int,
                     testId: Int,
                     navigationController: NavController,
                     viewModel: CameraViewModel = hiltViewModel()
) {
    CameraContent(applicationContext = applicationContext,
        outputDirectory = outputDirectory,
        onBackClick = { navigationController.navigateUp()},
        onPhotoTaken = { uri: Uri -> viewModel.insertTestAttachment(PartTestAttachment(testId, partId, UUID.randomUUID(), "$uri"))})

}