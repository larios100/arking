package com.example.arking.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Prototype")
data class Prototype(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "name") var name: String,
    //@ColumnInfo(name = "content") var content: ByteArray
) {
    /*override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Prototype

        if (!content.contentEquals(other.content)) return false

        return true
    }

    override fun hashCode(): Int {
        return content.contentHashCode()
    }*/
}