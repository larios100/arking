package com.example.arking.presentation.parts

import com.example.arking.model.Part
import com.example.arking.model.PartAttachment
import com.example.arking.model.PartTask

data class PartState(
    val part: Part? = null,
    val partTasks: List<PartTask> = emptyList(),
    val partAttachments: List<PartAttachment> = emptyList(),
    val showGallery: Boolean = false
)