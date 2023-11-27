package com.example.arking.feature_otis.data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.arking.data.Converters
import com.example.arking.feature_otis.domain.model.Oti
import com.example.arking.feature_otis.domain.model.OtiConcepts

@Database(
    entities = [Oti::class, OtiConcepts::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class OtisDatabase: RoomDatabase() {

    abstract val otiDao: OtiDao

    companion object {
        const val DATABASE_NAME = "otis_db"
    }
}