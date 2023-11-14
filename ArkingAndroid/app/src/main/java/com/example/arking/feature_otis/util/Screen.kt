package com.example.arking.feature_otis.util

sealed class Screen(val route: String) {
    object OtisScreen: Screen("otis_screen")
    object AddEditNoteOti: Screen("add_edit_oti_screen")
}