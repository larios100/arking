package com.example.arking.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.arking.model.GalleryItem

@Composable
fun PartGallery(photos: List<GalleryItem>){
    //val partsTasks by viewModel.getAllPartAttachmentByPartId(partId).collectAsState(emptyList())
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 250.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        items(photos){ attach ->
            val painter = rememberAsyncImagePainter(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(attach.path)
                    .size(Size.ORIGINAL) // Set the target size to load the image at.
                    .build()
            )
            Image(painter = painter, contentDescription = null,modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .clip(RoundedCornerShape(size = 8.dp)),
                contentScale = ContentScale.Crop,)
        }
    }
}
