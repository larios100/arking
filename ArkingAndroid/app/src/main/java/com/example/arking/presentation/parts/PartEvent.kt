package com.example.arking.presentation.parts

sealed class PartEvent {
    data class SetCompleted(val taskId: Int, val completed:Boolean): PartEvent()
    object ToggleGallery:PartEvent()
}