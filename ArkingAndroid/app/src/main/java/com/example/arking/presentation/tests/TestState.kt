package com.example.arking.presentation.tests

import com.example.arking.feature_otis.domain.utils.SignType
import com.example.arking.model.GalleryItem
import com.example.arking.model.TestWithData
import com.example.arking.model.TestWithDataItem

data class TestState(
    val comments: String = "",
    val test: TestWithData = TestWithData(0,"",""),
    var items: List<TestWithDataItem> = emptyList(),
    val showGallery: Boolean = false,
    val photos: List<GalleryItem> = emptyList(),
    val isSinging: Boolean = false,
    val singType: SignType = SignType.Resident,
    val signAuditor: String? = null,
    val signResident: String? = null
)
