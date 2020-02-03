package ir.alirezanazari.domain.entity

data class VenueInputParams(
    val latLng: String,
    val limit: Int,
    val offset: Int
)