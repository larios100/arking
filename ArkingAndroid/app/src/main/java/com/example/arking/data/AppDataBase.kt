package com.example.arking.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.arking.data.contract.ContractDao
import com.example.arking.data.part.PartDao
import com.example.arking.data.prototype.PrototypeDao
import com.example.arking.data.task.TaskDao
import com.example.arking.data.test.TestDao
import com.example.arking.model.Contract
import com.example.arking.model.Prototype
import com.example.arking.model.Part
import com.example.arking.model.PartAttachment
import com.example.arking.model.PartTask
import com.example.arking.model.PartTest
import com.example.arking.model.PartTestAttachment
import com.example.arking.model.PartTestItem
import com.example.arking.model.PartTestItemAttachment
import com.example.arking.model.TaskAttachment

// Define las entidades que deseas incluir en la base de datos
@Database(entities = [Contract::class,Part::class, Prototype::class, PartTask::class, PartAttachment::class, TaskAttachment::class, PartTest::class, PartTestItem::class, PartTestAttachment::class, PartTestItemAttachment::class], version = 3)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    // Define los DAO (Data Access Object) asociados a cada entidad
    abstract fun ContractDao(): ContractDao
    abstract fun PartDao(): PartDao
    abstract fun PrototypeDao(): PrototypeDao
    abstract fun TaskDao(): TaskDao
    abstract fun TestDao(): TestDao
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "arking_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}
