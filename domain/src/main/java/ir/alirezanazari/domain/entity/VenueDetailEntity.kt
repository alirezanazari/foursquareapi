package ir.alirezanazari.domain.entity

data class VenueDetailEntity(
    val id: String,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val address: String ?= "",
    val categoryType: String,
    val categoryIcon: String,
    val picture: String ,
    val likes: Int
)