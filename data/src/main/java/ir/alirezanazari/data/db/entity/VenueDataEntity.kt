package ir.alirezanazari.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import ir.alirezanazari.data.db.FourSquareDatabase.Companion.VENUE_TABLE_NAME

@Entity(tableName = VENUE_TABLE_NAME)
data class VenueDataEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String? = "",
    val distance: Int,
    val categoryType: String,
    val categoryIcon: String,
    val picture: String,
    val userLocation: String
)